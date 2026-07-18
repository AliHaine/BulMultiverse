package com.alihaine.bulmultiverse.file;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.world.WorldData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WorldsFile {
    private final File file;
    private final FileConfiguration fileConfiguration;

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
            Bukkit.getLogger().severe("Major error with the worlds.yml");
            e.printStackTrace();
        }
        if (fileConfiguration.getConfigurationSection("worlds") == null)
            this.loadDefaultWorld();
    }

    public void saveFile() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error when trying to save worlds to file");
        }
    }

    public void saveWorldDataToFile(WorldData worldData) {
        fileConfiguration.createSection("worlds." + worldData.getWorldName(), worldData.dumpsWorldDataForSave());
        saveFile();
    }

    public void removeWorldFromFile(String worldName) {
        fileConfiguration.set("worlds." + worldName, null);
        saveFile();
    }

    public void extractWorldsFromFile() {
        ConfigurationSection worldsSection = fileConfiguration.getConfigurationSection("worlds");
        if (worldsSection == null)
            return;

        for (String worldName : worldsSection.getKeys(false)) {
            if (!isWorldFolderExisting(worldName)) {
                Bukkit.getLogger().info("The world " + worldName + " folder don't exist, but are in worlds.yml. Remove from worlds.yml");
                removeWorldFromFile(worldName);
                continue;
            }

            WorldData worldData = new WorldData(fileConfiguration.getConfigurationSection("worlds." + worldName));
            worldData.createWorld(Bukkit.getConsoleSender());
            saveWorldDataToFile(worldData);
        }
    }

    public boolean isWorldFolderExisting(String worldName) {
        System.out.println(Bukkit.getServer().getWorldContainer().getPath());
        File worldFolder = new File(Bukkit.getServer().getWorldContainer(), worldName);
        if (worldFolder.exists() && worldFolder.isDirectory()) return true;

        //For minecraft versions where worlds are in the folder 'dimensions'
        for (World world : Bukkit.getServer().getWorlds()) {
            worldFolder = world.getWorldFolder().getParentFile();
            File nestedWorldFolder = new File(worldFolder, worldName);
            if (nestedWorldFolder.exists() && nestedWorldFolder.isDirectory()) return true;
        }

        return false;
    }

    private void loadDefaultWorld() {
        this.saveWorldDataToFile(new WorldData(Bukkit.getWorlds().get(0).getName(), new HashMap<>()));
    }
}
