package com.loficostudios.floralcraftapi.world.event;

import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class FloralWorldEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final FloralWorld world;

    public FloralWorldEvent(FloralWorld world) {
        this.world = world;
    }

    public FloralWorld getWorld() {
        return world;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
