package com.alihaine.bulmultiverse.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.alihaine.bulmultiverse.BulMultiverse;
import org.bukkit.command.CommandSender;

@CommandAlias(BaseBmvCommand.commandRootAlias)
public class BaseBmvCommand extends BaseCommand {

    /*
     * All commands must use this variable as the root alias.
     */
    public static final String commandRootAlias = "mv|bmv|bulmv|bulmultiverse";

    @Default
    public void onDefault(CommandSender sender) {
        sender.sendMessage("§eBulMultiverse §8| §6Version: " + BulMultiverse.getBulMultiverse().getDescription().getVersion());
        sender.sendMessage("§dDiscord support §8| §5https://discord.com/invite/wxnTV68dX2");
        sender.sendMessage("§a/bmv help §8| §2Display all commands");
    }
}
