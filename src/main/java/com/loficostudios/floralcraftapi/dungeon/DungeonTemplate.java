package com.loficostudios.floralcraftapi.dungeon;

public interface DungeonTemplate {
    Dungeon create();
    String getId();
}
