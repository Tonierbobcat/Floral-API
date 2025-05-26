package com.loficostudios.floralcraftapi.dungeon;

import com.loficostudios.floralcraftapi.dungeon.room.RoomData;
import com.loficostudios.floralcraftapi.utils.BlockLocation;
import com.loficostudios.floralcraftapi.utils.Bounds;

import java.util.List;
import java.util.Map;

public interface DungeonConfig {
    Map<String, RoomData> getRooms();
    Map<BlockLocation, LootTable> getChests();
    Bounds getBounds();
    String getBossId();
    String getBossRoomId();
    BlockLocation getBossSpawnLocation();
}
