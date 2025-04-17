package com.loficostudios.floralcraftapi.world.event;

import com.loficostudios.floralcraftapi.world.FloralWorld;
import com.loficostudios.floralcraftapi.world.WorldEvent;
import org.bukkit.event.Cancellable;

public class FloralWorldEventStartEvent extends FloralWorldEvent implements Cancellable {
    private final WorldEvent event;
    private boolean cancelled;

    public FloralWorldEventStartEvent(FloralWorld world, WorldEvent event) {
        super(world);
        this.event = event;
    }
    public WorldEvent getEvent() {
        return event;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
