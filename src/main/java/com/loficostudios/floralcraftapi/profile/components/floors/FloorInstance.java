package com.loficostudios.floralcraftapi.profile.components.floors;

import com.loficostudios.floralcraftapi.floor.Floor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FloorInstance {
    private final Floor floor;
    private boolean complete;

    private final HashMap<String, Long> dungeonsCompleted = new HashMap<>();

    public FloorInstance(Floor floor) {
        this.floor = floor;
    }

    public void completeDungeon(String id) {
        dungeonsCompleted.put(id, System.currentTimeMillis());
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Floor getFloor() {
        return floor;
    }

    public boolean isComplete() {
        return complete;
    }

    public Collection<String> getCompletedDungeons() {
        return dungeonsCompleted.keySet();
    }
}
