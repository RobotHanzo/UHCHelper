package dev.robothanzo.uhchelper.modes.UHC;

import dev.robothanzo.uhchelper.UHCHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class UHCDeathListener implements Listener {
    UHCHelper plugin;

    public UHCDeathListener(UHCHelper plg) {
        plugin = plg;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.getCfg().getBoolean("modes.uhc.death-watcher")){
            Player deadPlayer = event.getEntity().getPlayer();
            if (deadPlayer != null) {
                deadPlayer.setGameMode(GameMode.SPECTATOR);
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (!p.equals(deadPlayer)) {
                        p.sendMessage(ChatColor.RED + deadPlayer.getName() + " 已死亡！");
                    }
                });
                deadPlayer.sendMessage(ChatColor.RED + "你已經死亡並被切換為旁觀者模式");
            }
        }
    }
}
