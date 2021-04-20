package dev.robothanzo.uhchelper;

import dev.robothanzo.uhchelper.commands.Autocomplete;
import dev.robothanzo.uhchelper.commands.MainCommand;
import dev.robothanzo.uhchelper.modes.RegistrationService;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class UHCHelper extends JavaPlugin {
    public Config cfg;

    @Override
    public void onEnable() {
        cfg = new Config(this);

        new RegistrationService(this);

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
