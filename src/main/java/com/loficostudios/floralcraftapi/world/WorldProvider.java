package com.loficostudios.floralcraftapi.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface WorldProvider {
    public TeleportResult teleport(Player player, World world);

    public CompletableFuture<TeleportResult> teleportAsync(Player player, World world);

    public Location getSpawn(World world);
}
