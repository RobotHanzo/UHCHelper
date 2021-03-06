package dev.robothanzo.uhchelper.gui.uhc;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.gui.main.MainGUI;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class UHCGUI {
    public UHCGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(2, ChatColor.DARK_GRAY + "基本UHC設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 2);

        ItemStack countdownItem = new CustomItem(Material.PLAYER_HEAD,
                ChatColor.DARK_RED + "玩家死亡成為旁觀者：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.death-watcher")),
                ChatColor.WHITE + "任意鍵 切換開關");
        pane.addItem(new GuiItem(countdownItem, e -> {
            cfg.setValue("modes.uhc.death-watcher", !cfg.getBoolean("modes.uhc.death-watcher"));
            cfg.save();
            new UHCGUI(plugin, player);
        }));

        ItemStack borderItem = new CustomItem(Material.BARRIER,
                ChatColor.RED + "初始邊界：" + cfg.getInt("modes.uhc.border-size") +
                        "格 " + ChatColor.GREEN + "中心 x:" + ChatColor.WHITE + cfg.getInt("modes.uhc.border-x") + ChatColor.GREEN +
                        " y:" + ChatColor.WHITE + cfg.getInt("modes.uhc.border-y") + ChatColor.GREEN +
                        " z:" + ChatColor.WHITE + cfg.getInt("modes.uhc.border-z"),
                ChatColor.WHITE + "左鍵 +10",
                ChatColor.WHITE + "右鍵 +10",
                ChatColor.GREEN + "中鍵 將目前玩家所在位置設為中心",
                ChatColor.YELLOW + "Shift + 左鍵 +100",
                ChatColor.YELLOW + "Shift + 右鍵 + 100");
        pane.addItem(new GuiItem(borderItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.uhc.border-size", cfg.getInt("modes.uhc.border-size") + 10);
            }
            if (e.isRightClick()) {
                cfg.setValue("modes.uhc.border-size", cfg.getInt("modes.uhc.border-size") - 10);
            }
            if (e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.CREATIVE) {
                cfg.setValue("modes.uhc.border-x", Math.floor(player.getLocation().getX()));
                cfg.setValue("modes.uhc.border-y", Math.floor(player.getLocation().getY()));
                cfg.setValue("modes.uhc.border-z", Math.floor(player.getLocation().getZ()));
            }
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    cfg.setValue("modes.uhc.border-size", cfg.getInt("modes.uhc.border-size") + 90);
                }
                if (e.isRightClick()) {
                    cfg.setValue("modes.uhc.border-size", cfg.getInt("modes.uhc.border-size") - 90);
                }
            }
            cfg.save();
            World world = player.getWorld();
            WorldBorder border = world.getWorldBorder();
            border.setSize(cfg.getInt("modes.uhc.border-size"));
            border.setCenter(new Location(world, cfg.getInt("modes.uhc.border-x"),
                    cfg.getInt("modes.uhc.border-y"),
                    cfg.getInt("modes.uhc.border-z")));
            new UHCGUI(plugin, player);
        }));

        ItemStack generateChunkItem = new CustomItem(Material.GRASS_BLOCK,
                ChatColor.DARK_GREEN + "跑圖",
                ChatColor.WHITE + "將跑圖跑至初始邊界");
        pane.addItem(new GuiItem(generateChunkItem, e -> {
            e.setCancelled(true);
            final Semaphore working = new Semaphore(50);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                World world = player.getWorld();
                Chunk centerChunk = player.getChunk();
                long totalChunks = Math.round(cfg.getInt("modes.uhc.border-size") / 2.0 / 16.0) * Math.round(cfg.getInt("modes.uhc.border-size") / 2.0 / 16.0);
                AtomicLong currentChunk = new AtomicLong(0L);
                IntStream.range(0, (int) Math.round(cfg.getInt("modes.uhc.border-size") / 2.0 / 16.0)).forEach(x -> {
                    x += centerChunk.getX();
                    int finalX = x;
                    IntStream.range(0, (int) Math.round(cfg.getInt("modes.uhc.border-size") / 2.0 / 16.0)).forEach(z -> {
                        z += centerChunk.getZ();
                        if (!PaperLib.isChunkGenerated(world, finalX, z)) {
                            try {
                                working.acquire();
                            } catch (InterruptedException interruptedException) {
                                player.sendMessage(ChatColor.RED + "something went wrong");
                            }
                            PaperLib.getChunkAtAsync(world, finalX, z).thenRun(working::release);
                        }
                        currentChunk.addAndGet(1);
                        if (currentChunk.get() % 100 == 0) {
                            Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "跑圖進度： " + currentChunk + " / " + totalChunks);
                        }
                    });
                });
                Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "跑圖進度： " + totalChunks + " / " + totalChunks);
            });
        }));

        ItemStack teamDamageItem = new CustomItem(Material.GOLDEN_SWORD,
                ChatColor.GOLD + "同隊傷害：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.team-damage")),
                ChatColor.WHITE + "任意鍵 切換");
        pane.addItem(new GuiItem(teamDamageItem, e -> {
            cfg.setValue("modes.uhc.team-damage", !cfg.getBoolean("modes.uhc.team-damage"));
            World world = player.getWorld();
            cfg.save();
            new UHCGUI(plugin, player);
        }));

        ItemStack teamPushItem = new CustomItem(Material.PISTON,
                ChatColor.GRAY + "同隊推擠：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.team-push")),
                ChatColor.WHITE + "任意鍵 切換");
        pane.addItem(new GuiItem(teamPushItem, e -> {
            cfg.setValue("modes.uhc.team-push", !cfg.getBoolean("modes.uhc.team-push"));
            cfg.save();
            new UHCGUI(plugin, player);
        }));

        ItemStack tabHealthItem = new CustomItem(Material.GOLDEN_APPLE,
                ChatColor.GOLD + "TAB顯示血量：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.tab-health")),
                ChatColor.WHITE + "任意鍵 切換");
        pane.addItem(new GuiItem(tabHealthItem, e -> {
            cfg.setValue("modes.uhc.tab-health", !cfg.getBoolean("modes.uhc.tab-health"));
            cfg.save();
            new UHCGUI(plugin, player);
        }));

        ItemStack belowNameHealthItem = new CustomItem(Material.ENCHANTED_GOLDEN_APPLE,
                ChatColor.GOLD + "名條下方血量：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.below-name-health")),
                ChatColor.WHITE + "任意鍵 切換");
        pane.addItem(new GuiItem(belowNameHealthItem, e -> {
            cfg.setValue("modes.uhc.below-name-health", !cfg.getBoolean("modes.uhc.below-name-health"));
            cfg.save();
            new UHCGUI(plugin, player);
        }));

        ItemStack achievementNotificationItem = new CustomItem(Material.KNOWLEDGE_BOOK,
                ChatColor.GREEN + "成就廣播：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.achievement-notification")),
                ChatColor.WHITE + "任意鍵 切換");
        pane.addItem(new GuiItem(achievementNotificationItem, e -> {
            cfg.setValue("modes.uhc.achievement-notification", !cfg.getBoolean("modes.uhc.achievement-notification"));
            cfg.save();
            player.getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, cfg.getBoolean("modes.uhc.achievement-notification"));
            new UHCGUI(plugin, player);
        }));

        int i;
        GuiItem glassPane = MainGUI.getPlaceholdingItem();
        for (i = 0; i < 9; i++) {
            pane.addItem(glassPane);
        }

        pane.addItem(MainGUI.getBackItem(plugin, player));

        chestGui.setOnGlobalClick(e -> e.setCancelled(true));
        chestGui.getInventoryComponent().addPane(pane);
        chestGui.show(player);
    }

    public static ItemStack getMainGUIItem() {
        return new CustomItem(Material.GOLDEN_APPLE,
                ChatColor.RED + "基本UHC設定",
                ChatColor.GREEN + "設置所有有關基本UHC的東西");
    }
}
