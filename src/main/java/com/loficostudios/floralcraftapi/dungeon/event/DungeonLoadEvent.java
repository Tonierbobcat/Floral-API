package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DungeonLoadEvent extends DungeonEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    protected DungeonLoadEvent(Dungeon dungeon) {
        super(dungeon);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
