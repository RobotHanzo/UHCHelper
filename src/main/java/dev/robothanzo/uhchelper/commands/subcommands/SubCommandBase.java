package dev.robothanzo.uhchelper.commands.subcommands;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.commands.MainCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public abstract class SubCommandBase {
    protected final UHCHelper plugin;
    protected final MainCommand cmd;

    private final String name;
    private final boolean hidden;

    @ParametersAreNonnullByDefault
    protected SubCommandBase(UHCHelper plugin, MainCommand cmd, String name, boolean hidden) {
        this.plugin = plugin;
        this.cmd = cmd;

        this.name = name;
        this.hidden = hidden;
    }

    @Nonnull
    public final String getName() {
        return name;
    }

    public final boolean isHidden() {
        return hidden;
    }

    public void recordUsage(@Nonnull Map<SubCommandBase, Integer> commandUsage) {
        commandUsage.merge(this, 1, Integer::sum);
    }

    public abstract void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args);

    @Nonnull
    public abstract String getDescription();
}
