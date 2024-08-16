package com.alihaine.bulmultiverse.command.subcommands;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.WorldOption;
import com.alihaine.bulmultiverse.WorldOptionManager;
import com.alihaine.bulmultiverse.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Set implements SubCommand {
    private final WorldOptionManager worldOptionManager = BulMultiverse.getWorldOptionManager();

    @Override
    public void executor(CommandSender sender, List<String> args) {
        World world = Bukkit.getWorld(args.get(0));
        if (world == null) {
            sender.sendMessage("§e[BULMultiverse] §cThe world §e" + args.get(0) + " §cis not found, check §e/bmv help");
            return;
        }
        try {
            WorldOption worldOption = worldOptionManager.getOption(args.get(1));
            worldOption.optionExecutor(args.get(2), world);
            sender.sendMessage("§e[BULMultiverse] §aYou set the value §e" + args.get(1) + ": " + args.get(2) + " §ato the world §e" + world.getName());
        } catch (Exception exception) {
            sender.sendMessage(exception.getMessage());
        }
        BulMultiverse.getWorldsFileInstance().saveWorldsToFile();
    }
}
