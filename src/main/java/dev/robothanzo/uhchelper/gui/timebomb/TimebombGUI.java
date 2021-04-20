package dev.robothanzo.uhchelper.gui.timebomb;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.gui.main.MainGUI;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimebombGUI {
    public TimebombGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(1, ChatColor.DARK_GRAY + "定時炸彈設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 1);

        ItemStack countdownItem = new CustomItem(Material.CLOCK,
                ChatColor.RED + "爆炸前倒數：" + ChatColor.GREEN + cfg.getInt("modes.timebomb.countdown-before-explosion"),
                ChatColor.WHITE + "左鍵 +1",
                ChatColor.WHITE + "右鍵 -1",
                ChatColor.YELLOW + "Shift + 左鍵 +10",
                ChatColor.YELLOW + "Shift + 右鍵 -10");
        pane.addItem(new GuiItem(countdownItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.timebomb.countdown-before-explosion", cfg.getInt("modes.timebomb.countdown-before-explosion") + 1);
            }
            if (e.isRightClick()) {
                cfg.setValue("modes.timebomb.countdown-before-explosion", cfg.getInt("modes.timebomb.countdown-before-explosion") - 1);
            }
            // It's 9 because we would have + / - 1 up there
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    cfg.setValue("modes.timebomb.countdown-before-explosion", cfg.getInt("modes.timebomb.countdown-before-explosion") + 9);
                }
                if (e.isRightClick()) {
                    cfg.setValue("modes.timebomb.countdown-before-explosion", cfg.getInt("modes.timebomb.countdown-before-explosion") - 9);
                }
            }
            cfg.save();
            new TimebombGUI(plugin, player);
        }));

        ItemStack powerItem = new CustomItem(Material.WOODEN_SWORD,
                ChatColor.RED + "爆炸威力：" + ChatColor.GREEN + cfg.getInt("modes.timebomb.explosion-power"),
                ChatColor.WHITE + "左鍵 +1",
                ChatColor.WHITE + "右鍵 -1",
                ChatColor.YELLOW + "Shift + 左鍵 +10",
                ChatColor.YELLOW + "Shift + 右鍵 -10");
        pane.addItem(new GuiItem(powerItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.timebomb.explosion-power", cfg.getInt("modes.timebomb.explosion-power") + 1);
            }
            if (e.isRightClick()) {
                cfg.setValue("modes.timebomb.explosion-power", cfg.getInt("modes.timebomb.explosion-power") - 1);
            }
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    cfg.setValue("modes.timebomb.explosion-power", cfg.getInt("modes.timebomb.explosion-power") + 9);
                }
                if (e.isRightClick()) {
                    cfg.setValue("modes.timebomb.explosion-power", cfg.getInt("modes.timebomb.explosion-power") - 9);
                }
            }
            cfg.save();
            new TimebombGUI(plugin, player);
        }));

        int i;
        GuiItem glassPane = MainGUI.getPlaceholdingItem();
        for (i = 0; i < 6; i++) {
            pane.addItem(glassPane);
        }

        pane.addItem(MainGUI.getBackItem(plugin, player));

        chestGui.getInventoryComponent().addPane(pane);
        chestGui.show(player);
    }

    public static ItemStack getMainGUIItem(Config cfg) {
        ItemStack timebombItem = new CustomItem(Material.TNT,
                ChatColor.RED + "定時炸彈",
                ChatColor.GREEN + "在玩家死亡時放置一個含有其生前所持有之物品的箱子",
                ChatColor.RED + "並且在設定的時間後" + ChatColor.BOLD + "爆炸！",
                "",
                MainGUI.getStatusString(cfg.getBoolean("modes.timebomb.enabled")),
                ChatColor.WHITE + "爆炸前倒數(右鍵調整)：" + ChatColor.GREEN + cfg.getInt("modes.timebomb.countdown-before-explosion"),
                ChatColor.WHITE + "爆炸威力(右鍵調整)：" + ChatColor.GREEN + cfg.getInt("modes.timebomb.explosion-power"));
        if (cfg.getBoolean("modes.timebomb.enabled")) {
            ItemMeta meta = timebombItem.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 69, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            timebombItem.setItemMeta(meta);
        }
        return timebombItem;
    }
}

