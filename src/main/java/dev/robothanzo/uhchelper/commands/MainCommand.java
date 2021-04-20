package dev.robothanzo.uhchelper.commands;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.commands.subcommands.SubCommandBase;
import dev.robothanzo.uhchelper.commands.subcommands.Subcommands;
import io.github.thebusybiscuit.cscorelib2.chat.ChatColors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainCommand implements CommandExecutor, Listener {
    private final UHCHelper plugin;
    private final List<SubCommandBase> commands = new LinkedList<>();
    private final Map<SubCommandBase, Integer> commandUsage = new HashMap<>();

    public MainCommand(@Nonnull UHCHelper plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        commands.addAll(Subcommands.getAllCommands(this));
    }

    @Nonnull
    public UHCHelper getPlugin() {
        return plugin;
    }

    @Nonnull
    public Map<SubCommandBase, Integer> getCommandUsage() {
        return commandUsage;
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            for (SubCommandBase command : commands) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    command.recordUsage(commandUsage);
                    command.onExecute(sender, args);
                    return true;
                }
            }
        }

        sendHelp(sender);
        return !commands.isEmpty();
    }

    public void sendHelp(@Nonnull CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&aUHCHelper &2v" + getPlugin().getDescription().getVersion()));
        sender.sendMessage("");

        for (SubCommandBase cmd : commands) {
            if (!cmd.isHidden()) {
                sender.sendMessage(ChatColors.color("&3/uhc " + cmd.getName() + " &b") + cmd.getDescription());
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help uhchelper")) {
            sendHelp(e.getPlayer());
            e.setCancelled(true);
        }
    }

    @Nonnull
    public List<String> getSubCommandNames() {
        return commands.stream().map(SubCommandBase::getName).collect(Collectors.toList());
    }
}
