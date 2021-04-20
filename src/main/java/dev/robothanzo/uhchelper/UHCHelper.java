package dev.robothanzo.uhchelper;

import dev.robothanzo.uhchelper.commands.Autocomplete;
import dev.robothanzo.uhchelper.commands.MainCommand;
import dev.robothanzo.uhchelper.modes.Cutclean.BlockBreakListener;
import dev.robothanzo.uhchelper.modes.Cutclean.EntityDeathListener;
import dev.robothanzo.uhchelper.modes.LuckyLeaves.LeafBreakListener;
import dev.robothanzo.uhchelper.modes.NoClean.PlayerDeathListener;
import dev.robothanzo.uhchelper.modes.Timebomb.DeathListener;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class UHCHelper extends JavaPlugin {
    public Config cfg;

    @Override
    public void onEnable() {
        cfg = new Config(this);

        DeathListener timeBombDeathListener = new DeathListener(this);
        this.getServer().getPluginManager().registerEvents(timeBombDeathListener, this);

        BlockBreakListener blockBreakListener = new BlockBreakListener(this);
        EntityDeathListener entityDeathListener = new EntityDeathListener(this);
        this.getServer().getPluginManager().registerEvents(blockBreakListener, this);
        this.getServer().getPluginManager().registerEvents(entityDeathListener, this);

        LeafBreakListener leafBreakListener = new LeafBreakListener(this);
        this.getServer().getPluginManager().registerEvents(leafBreakListener, this);

        PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);
        this.getServer().getPluginManager().registerEvents(playerDeathListener, this);

        MainCommand mainCommand = new MainCommand(this);
        getCommand("uhchelper").setExecutor(mainCommand);
        getCommand("uhchelper").setTabCompleter(new Autocomplete(mainCommand));
        getCommand("uhc").setExecutor(mainCommand);
        getCommand("uhc").setTabCompleter(new Autocomplete(mainCommand));
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    @Override
    public void onDisable() {

    }

    public Config getCfg() {
        return cfg;
    }

    public UHCHelper getPlugin() {
        return this;
    }
}
