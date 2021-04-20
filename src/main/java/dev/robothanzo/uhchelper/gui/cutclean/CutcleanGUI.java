package dev.robothanzo.uhchelper.gui.cutclean;

import dev.robothanzo.uhchelper.gui.main.MainGUI;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CutcleanGUI {
    public static ItemStack getMainGUIItem(Config cfg) {
        ItemStack cutcleanItem = new CustomItem(Material.COOKED_BEEF,
                ChatColor.GRAY + "自動冶煉",
                ChatColor.GREEN + "自動將獲得的礦物、肉品轉換為燒過的",
                "",
                MainGUI.getStatusString(cfg.getBoolean("modes.cutclean.enabled")));
        if (cfg.getBoolean("modes.cutclean.enabled")) {
            ItemMeta meta = cutcleanItem.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_FIRE, 69, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            cutcleanItem.setItemMeta(meta);
        }
        return cutcleanItem;
    }
}
