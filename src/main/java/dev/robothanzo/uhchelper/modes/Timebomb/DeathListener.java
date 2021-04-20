package dev.robothanzo.uhchelper.modes.Timebomb;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {
    private final UHCHelper plugin;

    public DeathListener(UHCHelper plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.getCfg().getBoolean("modes.timebomb.enabled")) {
            final Player player = event.getEntity();
            final Location loc = player.getLocation().clone();
            loc.add(0,1,0);
            Block block = loc.getBlock();

            block = block.getRelative(BlockFace.DOWN);
            block.setType(Material.CHEST);
            org.bukkit.block.data.type.Chest blockData = (org.bukkit.block.data.type.Chest) block.getBlockData();
            blockData.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
            block.setBlockData(blockData, true);

            Chest chest = (Chest) block.getState();

            block = block.getRelative(BlockFace.WEST);
            block.setType(Material.CHEST);
            blockData = (org.bukkit.block.data.type.Chest) block.getBlockData();
            blockData.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
            block.setBlockData(blockData, true);

            for (ItemStack item : event.getDrops()) {
                if (item == null || item.getType() == Material.AIR) {
                    continue;
                }
                chest.getInventory().addItem(item);
            }

            event.getDrops().clear();

            final ArmorStand stand = player.getWorld().spawn(chest.getLocation().clone().add(0, 1.25, 0.5), ArmorStand.class);

            stand.setCustomNameVisible(true);
            stand.setSmall(true);

            stand.setGravity(false);
            stand.setVisible(false);

            stand.setMarker(true);

            new BukkitRunnable() {
                private int time = plugin.getCfg().getInt("modes.timebomb.countdown-before-explosion") + 1; // add one for countdown.

                public void run() {
                    time--;

                    if (time == 0) {
                        loc.getBlock().setType(Material.AIR);
                        loc.getWorld().createExplosion(loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5, plugin.getCfg().getInt("modes.timebomb.explosion-power"), false, true);
                        loc.getWorld().strikeLightning(loc); // Using actual lightning to kill the items.
                        stand.remove();
                        cancel();
                    } else if (time == 1) {
                        stand.setCustomName(ChatColor.DARK_RED + String.valueOf(time) + "秒後銷毀");
                    } else if (time == 2) {
                        stand.setCustomName(ChatColor.RED + String.valueOf(time) + "秒後銷毀");
                    } else if (time == 3) {
                        stand.setCustomName(ChatColor.GOLD + String.valueOf(time) + "秒後銷毀");
                    } else if (time <= 15) {
                        stand.setCustomName(ChatColor.YELLOW + String.valueOf(time) + "秒後銷毀");
                    } else {
                        stand.setCustomName(ChatColor.GREEN + String.valueOf(time) + "秒後銷毀");
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }
    }
}
