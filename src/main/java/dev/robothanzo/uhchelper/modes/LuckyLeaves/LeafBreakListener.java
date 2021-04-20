package dev.robothanzo.uhchelper.modes.LuckyLeaves;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class LeafBreakListener implements Listener {
    UHCHelper plugin;

    public LeafBreakListener(UHCHelper plg) {
        plugin = plg;
    }

    public boolean getRandomBoolean(float perc) {
        return Math.random() < perc;
    }

    @EventHandler
    public void onLeafBreak(BlockBreakEvent event) {
        if (plugin.getCfg().getBoolean("modes.luckyleaves.enabled")) {
            Material blk = event.getBlock().getType();
            if (blk == Material.ACACIA_LEAVES ||
                    blk == Material.BIRCH_LEAVES ||
                    blk == Material.DARK_OAK_LEAVES ||
                    blk == Material.OAK_LEAVES ||
                    blk == Material.JUNGLE_LEAVES ||
                    blk == Material.SPRUCE_LEAVES) {
                if (getRandomBoolean(plugin.getCfg().getFloat("modes.luckyleaves.golden_apple_chance"))) {
                    plugin.getLogger().log(Level.SEVERE, "ok 3");
                    event.getPlayer().sendMessage(ChatColor.GOLD + "幸運之神眷顧你！你獲得了 " + ChatColor.BOLD + "金蘋果！");
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                }
            }
        }
    }
}
