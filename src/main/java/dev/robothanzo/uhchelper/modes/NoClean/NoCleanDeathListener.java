package dev.robothanzo.uhchelper.modes.NoClean;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class NoCleanDeathListener implements Listener {
    UHCHelper plugin;

    public NoCleanDeathListener(UHCHelper plg) {
        plugin = plg;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.getCfg().getBoolean("modes.noclean.enabled")) {
            Player killer = event.getEntity().getKiller();
            int potionDuration = plugin.getCfg().getInt("modes.noclean.timeout") * 20;
            if (killer != null) {
                killer.addPotionEffects(
                        Arrays.asList(
                                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, potionDuration, 69, true, false),
                                new PotionEffect(PotionEffectType.WEAKNESS, potionDuration, 69, true, false)));
            }
        }
    }
}
