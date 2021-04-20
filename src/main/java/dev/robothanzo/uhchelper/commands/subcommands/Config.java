package dev.robothanzo.uhchelper.commands.subcommands;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.commands.MainCommand;
import dev.robothanzo.uhchelper.gui.main.MainGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class Config extends SubCommandBase{
    UHCHelper plugin;
    protected Config(UHCHelper plg, MainCommand cmd) {
        super(plg, cmd, "config", false);
        plugin = plg;
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        new MainGUI(plugin, (Player) sender);
    }

    @Nonnull
    @Override
    public String getDescription() {
        return "設定各個模式";
    }
}
