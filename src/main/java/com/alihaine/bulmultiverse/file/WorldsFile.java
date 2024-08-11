package com.alihaine.bulmultiverse.file;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.WorldOptionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WorldsFile {
    private File file;
    private FileConfiguration fileConfiguration;
    private final WorldOptionManager worldOptionManager = BulMultiverse.getWorldOptionManager();

    public WorldsFile(BulMultiverse bulMultiverseInstance) {
        file = new File(bulMultiverseInstance.getDataFolder(), "worlds.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            bulMultiverseInstance.saveResource("worlds.yml", false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("[BulMultiverse] Error with the worlds saver file");
            e.printStackTrace();
        }
    }

    public FileConfiguration getWorldsFile() {
        return this.fileConfiguration;
    }

    public void saveWorldsToFile() {
        for (World world: Bukkit.getWorlds()) {
            WorldData worldData = new WorldData(world);
            fileConfiguration.createSection("worlds." + world.getName(), worldData.dumpsForSave());
        }
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[BulMultiverse] Error when trying to save worlds to file");
        }
    }

    public void createWorldsFromFile() {
        ConfigurationSection worldsSection = fileConfiguration.getConfigurationSection("worlds");
        for (String worldName : worldsSection.getKeys(false)) {
            ConfigurationSection worldSection = fileConfiguration.getConfigurationSection("worlds." + worldName);
            WorldCreator newWorldCreator = new WorldCreator(worldName);
            worldSection.getValues(false).forEach((key, value) -> {
                try {
                    worldOptionManager.getOption(key).optionExecutor((String) value, newWorldCreator);
                } catch (Exception exception) {
                    Bukkit.getConsoleSender().sendMessage("The option " + key + " is not found or supported.");
                }
            });
            newWorldCreator.createWorld();
        }
    }

}
