package dev.robothanzo.uhchelper.gui.uhc;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.gui.main.MainGUI;
import dev.robothanzo.uhchelper.gui.timebomb.TimebombGUI;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UHCGUI {
    public UHCGUI(UHCHelper plugin, Player player) {
        Config cfg = plugin.getCfg();
        ChestGui chestGui = new ChestGui(1, ChatColor.DARK_GRAY + "基本UHC設定");
        OutlinePane pane = new OutlinePane(0, 0, 9, 1);

        ItemStack countdownItem = new CustomItem(Material.PLAYER_HEAD,
                ChatColor.RED + "玩家死亡成為旁觀者：" + MainGUI.getStatusString(cfg.getBoolean("modes.uhc.death-watcher")),
                ChatColor.WHITE + "左鍵 切換開關");
        pane.addItem(new GuiItem(countdownItem, e -> {
            if (e.isLeftClick()) {
                cfg.setValue("modes.uhc.death-watcher", !cfg.getBoolean("modes.uhc.death-watcher"));
            }
            cfg.save();
            new TimebombGUI(plugin, player);
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

    public static ItemStack getMainGUIItem() {
        return new CustomItem(Material.GOLDEN_APPLE,
                ChatColor.RED + "基本UHC設定",
                ChatColor.GREEN + "設置所有有關基本UHC的東西");
    }
}
