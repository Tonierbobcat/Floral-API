package com.loficostudios.floralcraftapi.dungeon;

import com.loficostudios.floralcraftapi.dungeon.room.DungeonRoom;
import com.loficostudios.floralcraftapi.utils.BlockLocation;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Dungeon extends FloralWorld {
    DungeonConfig getDungeonConfig();
    @Nullable DungeonRoom getRoom(Location location);
    @NotNull Collection<DungeonChest> getChests();
    @Nullable DungeonChest getChest(BlockLocation location);
    DungeonBoss spawnBoss(Location location);
    DungeonMob spawnMob(String id, double x, double y, double z);
}
