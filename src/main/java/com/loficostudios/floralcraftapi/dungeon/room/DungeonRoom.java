package com.loficostudios.floralcraftapi.dungeon.room;

import com.loficostudios.floralcraftapi.dungeon.room.spawner.DungeonSpawner;
import com.loficostudios.floralcraftapi.utils.Bounds;
import org.jetbrains.annotations.Nullable;

public interface DungeonRoom {
    String getId();
    Bounds getBounds();
    @Nullable DungeonSpawner getDungeonSpawner();
}
