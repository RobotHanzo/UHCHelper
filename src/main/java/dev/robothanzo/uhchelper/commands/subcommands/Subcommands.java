package dev.robothanzo.uhchelper.commands.subcommands;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.commands.MainCommand;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class Subcommands {

    private Subcommands() {
    }

    public static Collection<SubCommandBase> getAllCommands(MainCommand cmd) {
        UHCHelper plugin = cmd.getPlugin();
        List<SubCommandBase> commands = new LinkedList<>();

        commands.add(new Config(plugin, cmd));
        commands.add(new Reload(plugin, cmd));

        return commands;
    }
}