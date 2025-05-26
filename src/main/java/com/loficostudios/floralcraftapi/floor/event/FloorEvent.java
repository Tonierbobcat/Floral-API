package com.loficostudios.floralcraftapi.floor.event;

import com.loficostudios.floralcraftapi.floor.Floor;
import org.bukkit.event.Event;

public abstract class FloorEvent extends Event {
    private final Floor floor;

    protected FloorEvent(Floor floor) {
        this.floor = floor;
    }

    public Floor getFloor() {
        return floor;
    }
}
