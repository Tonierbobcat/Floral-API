package com.loficostudios.floralcraftapi.dungeon.room.spawner;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import com.loficostudios.floralcraftapi.dungeon.DungeonMob;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;

import java.util.Collection;

public interface DungeonSpawner {
    boolean isActive();
    void start(Dungeon dungeon);
    int getCurrentWaveIndex();
    SpawnerConfig getConfig();
    Collection<DungeonMob> getActiveMobs();
    DungeonSpawnerState getState();
    void setState(DungeonSpawnerState state);
}
