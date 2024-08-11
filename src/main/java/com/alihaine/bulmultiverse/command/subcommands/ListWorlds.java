package com.alihaine.bulmultiverse.command.subcommands;

import com.alihaine.bulmultiverse.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListWorlds implements SubCommand {
    @Override
    public void executor(CommandSender sender, java.util.List<String> args) {
        List<World> worlds = Bukkit.getWorlds();

        worlds.forEach(world -> {
            sender.sendMessage(world.getName());
        });
    }
}
