package com.loficostudios.floralcraftapi.dungeon.room;

import com.loficostudios.floralcraftapi.dungeon.room.spawner.SpawnerConfig;
import com.loficostudios.floralcraftapi.utils.Bounds;
import org.jetbrains.annotations.Nullable;

public record RoomData(Bounds bounds, @Nullable SpawnerConfig spawner) {
}
