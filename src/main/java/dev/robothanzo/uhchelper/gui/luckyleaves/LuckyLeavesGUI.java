package dev.robothanzo.uhchelper.gui.luckyleaves;

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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.math.MathContext;

public class LuckyLeavesGUI {
    public LuckyLeavesGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(1, ChatColor.DARK_GRAY + "幸運之葉設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 1);

        ItemStack chanceItem = new CustomItem(Material.EXPERIENCE_BOTTLE,
                ChatColor.RED + "金蘋果掉落機率：" + ChatColor.GREEN + cfg.getDouble("modes.luckyleaves.golden_apple_chance") * 100.0 + "%",
                ChatColor.WHITE + "左鍵 +0.1%",
                ChatColor.WHITE + "右鍵 -0.1%",
                ChatColor.RED + "中鍵 重製為1%",
                ChatColor.YELLOW + "Shift + 左鍵 +1%",
                ChatColor.YELLOW + "Shift + 右鍵 -1%");
        pane.addItem(new GuiItem(chanceItem, e -> {
            e.setCancelled(true);
            BigDecimal chance = BigDecimal.valueOf(cfg.getDouble("modes.luckyleaves.golden_apple_chance"));
            if (e.isLeftClick()) {
                cfg.setValue("modes.luckyleaves.golden_apple_chance", chance.add(new BigDecimal("0.001")));
            }
            if (e.isRightClick()) {
                cfg.setValue("modes.luckyleaves.golden_apple_chance", chance.subtract(new BigDecimal("0.001")));
            }
            if (e.getClick().equals(ClickType.MIDDLE) || e.getClick().equals(ClickType.CREATIVE)){
                cfg.setValue("modes.luckyleaves.golden_apple_chance", 0.01);
            }
            // It's 0.009 because we would have + / - 0.001 up there
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    cfg.setValue("modes.luckyleaves.golden_apple_chance", chance.add(new BigDecimal("0.009")));
                }
                if (e.isRightClick()) {
                    cfg.setValue("modes.luckyleaves.golden_apple_chance", chance.subtract(new BigDecimal("0.009")));
                }
            }
            cfg.save();
            new LuckyLeavesGUI(plugin, player);
        }));

        int i;
        GuiItem glassPane = MainGUI.getPlaceholdingItem();
        for (i = 0; i < 7; i++) {
            pane.addItem(glassPane);
        }

        pane.addItem(MainGUI.getBackItem(plugin, player));

        chestGui.getInventoryComponent().addPane(pane);
        chestGui.show(player);
    }

    public static ItemStack getMainGUIItem(Config cfg) {
        ItemStack cutcleanItem = new CustomItem(Material.OAK_LEAVES,
                ChatColor.DARK_GREEN + "幸運之葉",
                ChatColor.GREEN + "設定的機率內會掉落金蘋果",
                "",
                MainGUI.getStatusString(cfg.getBoolean("modes.luckyleaves.enabled")),
                ChatColor.WHITE + "金蘋果掉落機率(右鍵調整)：" + ChatColor.GREEN + cfg.getDouble("modes.luckyleaves.golden_apple_chance") * 100.0 + "%");
        if (cfg.getBoolean("modes.luckyleaves.enabled")) {
            ItemMeta meta = cutcleanItem.getItemMeta();
            meta.addEnchant(Enchantment.LUCK, 69, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            cutcleanItem.setItemMeta(meta);
        }
        return cutcleanItem;
    }
}
