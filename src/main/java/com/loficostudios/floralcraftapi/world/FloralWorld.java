package com.loficostudios.floralcraftapi.world;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.file.TypedDataContainer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface FloralWorld {

    World getBukkitWorld();

    TypedDataContainer getTypedDataContainer();

    default Collection<Player> getPlayers() {
        return getBukkitWorld().getPlayers();
    }

    default String getName() {
        return getBukkitWorld().getName();
    }

    TeleportResult teleport(Player player);

    Location getSpawnLocation();

    default boolean inWorld(Entity entity) {
        return inWorld(entity.getLocation());
    }

    default boolean inWorld(Location location) {
        if (getBukkitWorld() == null)
            return false;
        return location.getWorld().getName().equals(getBukkitWorld().getName());
    }
}
