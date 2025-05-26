package com.loficostudios.floralcraftapi.dungeon.room.spawner;

import java.util.*;

public class Wave {
    private final List<SpawnerEntry> mobs;
    public Wave(List<SpawnerEntry> mobs) {
        this.mobs = mobs;
    }
    public Wave() {
        this.mobs = new ArrayList<>();
    }
    public Collection<SpawnerEntry> getMobs() {
        return mobs;
    }
    public static class SpawnerEntry {
        private final String id;
        private final int amount;

        public SpawnerEntry(String id, int amount) {
            this.id = id;
            this.amount = amount;
        }

        public String getId() {
            return id;
        }

        public int getAmount() {
            return amount;
        }
    }
}
