package dev.robothanzo.uhchelper.gui.main;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.gui.cutclean.CutcleanGUI;
import dev.robothanzo.uhchelper.gui.luckyleaves.LuckyLeavesGUI;
import dev.robothanzo.uhchelper.gui.noclean.NoCleanGUI;
import dev.robothanzo.uhchelper.gui.timebomb.TimebombGUI;
import dev.robothanzo.uhchelper.gui.uhc.UHCGUI;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainGUI {
    public MainGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(1, ChatColor.DARK_GRAY + "設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 1);

        ItemStack uhcItem = UHCGUI.getMainGUIItem();
        pane.addItem(new GuiItem(uhcItem, e -> {
            if (e.isRightClick()) {
                new UHCGUI(plugin, player);
            }
        }));

        ItemStack timebombItem = TimebombGUI.getMainGUIItem(cfg);
        pane.addItem(new GuiItem(timebombItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.timebomb.enabled", !(cfg.getBoolean("modes.timebomb.enabled")));
                cfg.save();
                new MainGUI(plugin, player);
            }
            if (e.isRightClick()) {
                new TimebombGUI(plugin, player);
            }
        }));

        ItemStack cutcleanItem = CutcleanGUI.getMainGUIItem(cfg);
        pane.addItem(new GuiItem(cutcleanItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.cutclean.enabled", !(cfg.getBoolean("modes.cutclean.enabled")));
                cfg.save();
                new MainGUI(plugin, player);
            }
        }));

        ItemStack luckyLeavesItem = LuckyLeavesGUI.getMainGUIItem(cfg);
        pane.addItem(new GuiItem(luckyLeavesItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.luckyleaves.enabled", !(cfg.getBoolean("modes.luckyleaves.enabled")));
                cfg.save();
                new MainGUI(plugin, player);
            }
            if (e.isRightClick()) {
                new LuckyLeavesGUI(plugin, player);
            }
        }));

        ItemStack noCleanItem = NoCleanGUI.getMainGUIItem(cfg);
        pane.addItem(new GuiItem(noCleanItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.noclean.enabled", !(cfg.getBoolean("modes.noclean.enabled")));
                cfg.save();
                new MainGUI(plugin, player);
            }
            if (e.isRightClick()) {
                new NoCleanGUI(plugin, player);
            }
        }));

        chestGui.setOnGlobalClick(e -> e.setCancelled(true));
        chestGui.getInventoryComponent().addPane(pane);
        chestGui.show(player);
    }

    public static String getStatusString(boolean enabled) {
        if (enabled) {
            return ChatColor.WHITE + "啟用狀態(左鍵切換)：" + ChatColor.GREEN + "✔";
        } else {
            return ChatColor.WHITE + "啟用狀態(左鍵切換)：" + ChatColor.RED + "❌";
        }
    }

    public static GuiItem getBackItem(UHCHelper plugin, Player player) {
        ItemStack backItem = new CustomItem(Material.SPECTRAL_ARROW,
                ChatColor.GREEN + "返回",
                ChatColor.WHITE + "返回主頁面");
        return new GuiItem(backItem, e -> {
            e.setCancelled(true);
            new MainGUI(plugin, player);
        });
    }

    public static GuiItem getPlaceholdingItem() {
        return new GuiItem(new CustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "), e -> e.setCancelled(true));
    }
}
