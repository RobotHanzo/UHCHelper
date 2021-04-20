package dev.robothanzo.uhchelper.gui.noclean;

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

public class NoCleanGUI {
    public NoCleanGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(1, ChatColor.DARK_GRAY + "防撿尾刀設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 1);

        ItemStack timeoutItem = new CustomItem(Material.CLOCK,
                ChatColor.RED + "效果時效：" + ChatColor.GREEN + cfg.getInt("modes.noclean.timeout"),
                ChatColor.WHITE + "左鍵 +1",
                ChatColor.WHITE + "右鍵 -1",
                ChatColor.YELLOW + "Shift + 左鍵 +10",
                ChatColor.YELLOW + "Shift + 右鍵 -10");
        pane.addItem(new GuiItem(timeoutItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.noclean.timeout", cfg.getInt("modes.noclean.timeout") + 1);
            }
            if (e.isRightClick()) {
                cfg.setValue("modes.noclean.timeout", cfg.getInt("modes.noclean.timeout") - 1);
            }
            // It's 9 because we would have + / - 1 up there
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    cfg.setValue("modes.noclean.timeout", cfg.getInt("modes.noclean.timeout") + 9);
                }
                if (e.isRightClick()) {
                    cfg.setValue("modes.noclean.timeout", cfg.getInt("modes.noclean.timeout") - 9);
                }
            }
            cfg.save();
            new NoCleanGUI(plugin, player);
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
        ItemStack noCleanItem = new CustomItem(Material.IRON_SWORD,
                ChatColor.WHITE + "防撿尾刀",
                ChatColor.GREEN + "在玩家殺人後給予設定秒的抗性及虛弱，防止繼續殺人，並防止於弱勢時被擊殺",
                "",
                MainGUI.getStatusString(cfg.getBoolean("modes.noclean.enabled")),
                ChatColor.WHITE + "藥水時效(右鍵調整)：" + ChatColor.GREEN + cfg.getInt("modes.noclean.timeout"));
        if (cfg.getBoolean("modes.noclean.enabled")) {
            ItemMeta meta = noCleanItem.getItemMeta();
            meta.addEnchant(Enchantment.DAMAGE_ALL, 69, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            noCleanItem.setItemMeta(meta);
        }
        return noCleanItem;
    }
}
