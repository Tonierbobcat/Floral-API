package com.loficostudios.floralcraftapi.dungeon;

import com.loficostudios.floralcraftapi.world.entity.FloralEntity;

public interface DungeonMob extends FloralEntity {
    Dungeon getDungeon();
}
