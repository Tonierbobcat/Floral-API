package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import org.bukkit.event.Event;

public abstract class DungeonEvent extends Event {
    private final Dungeon dungeon;

    protected DungeonEvent(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
}
