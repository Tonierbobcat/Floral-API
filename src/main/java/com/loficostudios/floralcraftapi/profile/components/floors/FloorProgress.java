package com.loficostudios.floralcraftapi.profile.components.floors;

import com.loficostudios.floralcraftapi.floor.Floor;

import java.util.Collection;
import java.util.HashMap;

public class FloorProgress {
    private final HashMap<String, FloorInstance> floors = new HashMap<>();

    public FloorProgress(Collection<Floor> floors) {
        for (Floor floor : floors) {
            this.floors.put(floor.getId(), new FloorInstance(floor));
        }
    }

    public FloorInstance getInstance(String id) {
        return floors.get(id);
    }

    public FloorInstance getInstance(Floor floor) {
        return floors.get(floor.getId());
    }

    public Collection<FloorInstance> getInstances() {
        return floors.values();
    }
}
