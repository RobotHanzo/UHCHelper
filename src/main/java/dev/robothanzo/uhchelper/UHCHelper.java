package dev.robothanzo.uhchelper;

import dev.robothanzo.uhchelper.commands.Autocomplete;
import dev.robothanzo.uhchelper.commands.MainCommand;
import dev.robothanzo.uhchelper.modes.RegistrationService;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import org.apache.commons.io.FileDeleteStrategy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class UHCHelper extends JavaPlugin {
    public Config cfg;

    @Override
    public void onEnable() {
        cfg = new Config(this);

        if (cfg.getBoolean("settings.cleanup-world") && Bukkit.getWorld("world") == null) {
            try {
                File folder = new File("world");
                FileDeleteStrategy.FORCE.delete(folder);
                getLogger().log(Level.INFO, "Deleted overworld");
                // Generate stuff that minecraft doesnt generate for some reason
                folder = new File("world/playerdata");
                folder.mkdirs();
                folder = new File("world/datapacks");
                folder.mkdirs();
                File file = new File("world/uid.dat");
                file.createNewFile();
            } catch (IOException e) {
                getLogger().log(Level.WARNING, "Overworld deleting failed");
            }

            try {
                FileDeleteStrategy.FORCE.delete(new File("world_nether"));
                getLogger().log(Level.INFO, "Deleted nether");
            } catch (IOException e) {
                getLogger().log(Level.WARNING, "Nether deleting failed");
            }

            try {
                FileDeleteStrategy.FORCE.delete(new File("world_the_end"));
                getLogger().log(Level.INFO, "Deleted the end");
            } catch (IOException e) {
                getLogger().log(Level.WARNING, "The end deleting failed");
            }
        }

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
        cfg.save();
    }

    public Config getCfg() {
        return cfg;
    }

    public UHCHelper getPlugin() {
        return this;
    }
}
