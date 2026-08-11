package com.alihaine.bulmultiverse.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.concurrent.CompletableFuture;

public class BukkitWorldLoader implements WorldLoader {
    @Override
    public CompletableFuture<World> loadWorld(WorldCreator worldCreator) {
        return CompletableFuture.completedFuture(worldCreator.createWorld());
    }
}
