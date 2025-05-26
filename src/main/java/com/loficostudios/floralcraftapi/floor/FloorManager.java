package com.loficostudios.floralcraftapi.floor;

import com.loficostudios.floralcraftapi.world.WorldRequirement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface FloorManager {
    @Nullable Floor getNextFloor(Floor floor);

    @Nullable Floor getPreviousFloor(Floor target);

    Collection<WorldRequirement> getRequirements(Floor target) ;

    void registerFloor(Floor floor);

    @Nullable Floor getFloor(@NotNull Player player);

    Collection<Floor> getFloors();

    @Nullable Floor getFloor(String id);
}
