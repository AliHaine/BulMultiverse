package com.alihaine.bulmultiverse.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.concurrent.CompletableFuture;

/**
 * Loads or creates worlds. Addons can provide another implementation for
 * server software that does not support {@link WorldCreator#createWorld()}.
 * The future must be completed from a thread on which the loaded world's
 * options can safely be accessed and modified.
 */
@FunctionalInterface
public interface WorldLoader {
    CompletableFuture<World> loadWorld(WorldCreator worldCreator);
}
