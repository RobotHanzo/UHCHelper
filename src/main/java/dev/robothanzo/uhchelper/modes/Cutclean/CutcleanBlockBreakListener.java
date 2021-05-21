package dev.robothanzo.uhchelper.modes.Cutclean;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class CutcleanBlockBreakListener implements Listener {
    UHCHelper plugin;
    public CutcleanBlockBreakListener(UHCHelper plg){
        plugin = plg;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getCfg().getBoolean("modes.cutclean.enabled")) {
            Block block = event.getBlock();
            if (block.getType() == Material.IRON_ORE) {
                block.setType(Material.AIR);
                block.getState().update();
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);
                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(1);
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
            }
            if (block.getType() == Material.GOLD_ORE) {
                block.setType(Material.AIR);
                block.getState().update();
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);
                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(1);
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
            }
        }
    }
}
