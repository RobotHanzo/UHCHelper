package dev.robothanzo.uhchelper.modes.Cutclean;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class CutcleanEntityDeathListener implements Listener {
    UHCHelper plugin;

    public CutcleanEntityDeathListener(UHCHelper plg) {
        plugin = plg;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (plugin.getCfg().getBoolean("modes.cutclean.enabled")) {
            Entity entity = event.getEntity();
            ItemStack drops;
            Iterator var4;
            if (entity.getType() == EntityType.CHICKEN) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.CHICKEN) {
                        drops.setType(Material.COOKED_CHICKEN);
                    }
                }
            }

            if (entity.getType() == EntityType.COW) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.BEEF) {
                        drops.setType(Material.COOKED_BEEF);
                    }
                }
            }

            if (entity.getType() == EntityType.PIG) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.PORKCHOP) {
                        drops.setType(Material.COOKED_PORKCHOP);
                    }
                }
            }

            if (entity.getType() == EntityType.SHEEP) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.MUTTON) {
                        drops.setType(Material.COOKED_MUTTON);
                    }
                }
            }

            if (entity.getType() == EntityType.COD) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.COD) {
                        drops.setType(Material.COOKED_COD);
                    }
                }
            }

            if (entity.getType() == EntityType.SALMON) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.SALMON) {
                        drops.setType(Material.COOKED_SALMON);
                    }
                }
            }

            if (entity.getType() == EntityType.RABBIT) {
                var4 = event.getDrops().iterator();

                while (var4.hasNext()) {
                    drops = (ItemStack) var4.next();
                    if (drops.getType() == Material.RABBIT) {
                        drops.setType(Material.COOKED_RABBIT);
                    }
                }
            }
        }
    }
}
