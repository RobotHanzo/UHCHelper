package dev.robothanzo.uhchelper.commands.subcommands;

import dev.robothanzo.uhchelper.UHCHelper;
import dev.robothanzo.uhchelper.commands.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Reload extends SubCommandBase {
    protected Reload(UHCHelper plugin, MainCommand cmd) {
        super(plugin, cmd, "reload", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        plugin.onEnable();
        sender.sendMessage(ChatColor.GREEN + "重新載入成功！");
    }

    @Nonnull
    @Override
    public String getDescription() {
        return "重新載入插件設定檔案";
    }
}
