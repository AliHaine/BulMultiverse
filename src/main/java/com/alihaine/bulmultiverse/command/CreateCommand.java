package com.alihaine.bulmultiverse.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.file.ConfigFile;
import com.alihaine.bulmultiverse.file.Message;
import com.alihaine.bulmultiverse.world.WorldData;
import com.alihaine.bulmultiverse.world.WorldOption;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;


@CommandAlias(BaseBmvCommand.commandRootAlias)
@CommandPermission("bulmultiverse.create")
public class CreateCommand extends BaseCommand {
    @Subcommand("create")
    @Description("Create a new world")
    @Syntax("/bmv create [World Name] (Flags)")
    public void onCreate(CommandSender sender, String worldName, String[] flags) {
        Map<WorldOption, Object> convertToOptionString = new HashMap<>();

        if (ConfigFile.isDisableWorldName(worldName)) {
            new Message("forbidden_world_name").sendMessage(sender);
            return;
        }

        if (BulMultiverse.getWorldDataManager().isWorldAlreadyCreated(worldName) || worldName.equalsIgnoreCase("overworld") ) {
            new Message("world_already_exist").sendMessage(sender);
            return;
        }

        for (int i = 0; i < flags.length - 1; i+=2) {
            String flag = flags[i];
            try {
                convertToOptionString.put(BulMultiverse.getWorldOptionManager().getOption(flag), flags[i+1]);
            } catch (Exception exception) {
                new Message("flag_not_found")
                        .withPlaceHolder("name", flag)
                        .sendMessage(sender);
            }
        }

        WorldData worldData = new WorldData(worldName, convertToOptionString);
        worldData.createWorld(sender).thenAccept(
                BulMultiverse.getWorldsFile()::saveWorldDataToFile);
    }
}
