package dev.robothanzo.uhchelper;

import dev.robothanzo.uhchelper.commands.Autocomplete;
import dev.robothanzo.uhchelper.commands.MainCommand;
import dev.robothanzo.uhchelper.modes.RegistrationService;
import io.github.thebusybiscuit.cscorelib2.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class UHCHelper extends JavaPlugin {
    public Config cfg;

    private boolean deleteFolder(File worldDir){
        File[] allContents = worldDir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolder(file);
            }
        }
        return worldDir.delete();
    }

    @Override
    public void onEnable() {
        cfg = new Config(this);

        if (cfg.getBoolean("settings.cleanup-world") && Bukkit.getWorld("world") == null) {
            getLogger().log(Level.WARNING, "Deleting the previous world...");
            if (deleteFolder(new File("world"))){
                getLogger().log(Level.INFO, "Deleted overworld");
            }
            if (deleteFolder(new File("world_nether"))){
                getLogger().log(Level.INFO, "Deleted nether");
            }
            if (deleteFolder(new File("world_the_end"))){
                getLogger().log(Level.INFO, "Deleted the end");
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

    }

    public Config getCfg() {
        return cfg;
    }

    public UHCHelper getPlugin() {
        return this;
    }
}
