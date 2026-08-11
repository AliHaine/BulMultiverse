package com.alihaine.bulmultiverse.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.file.Message;
import com.alihaine.bulmultiverse.world.WorldData;
import com.alihaine.bulmultiverse.world.WorldOption;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.HashMap;

@CommandAlias(BaseBmvCommand.commandRootAlias)
@CommandPermission("bulmultiverse.load")
public class LoadCommand extends BaseCommand {
    @Subcommand("load")
    @Description("Load existing world")
    @Syntax("/bmv load [world_name]")
    public void onLoad(CommandSender sender, String targetWorld) throws Exception {
        if (!BulMultiverse.getWorldsFile().isWorldFolderExisting(targetWorld)) {
            new Message("world_not_found").withPlaceHolder("name", targetWorld).sendMessage(sender);
            return;
        }

        final HashMap<WorldOption, Object> options = new HashMap<>();
        File file = new File(Bukkit.getWorldContainer(), targetWorld);
        if (new File(file, "DIM-1").exists())
            options.put(BulMultiverse.getWorldOptionManager().getOption("-e"), "nether");
        else if (new File(file, "DIM1").exists())
            options.put(BulMultiverse.getWorldOptionManager().getOption("-e"), "the_end");

        WorldData worldData = new WorldData(targetWorld, options);
        worldData.createWorld(sender).thenAccept(
                BulMultiverse.getWorldsFile()::saveWorldDataToFile);
    }
}
