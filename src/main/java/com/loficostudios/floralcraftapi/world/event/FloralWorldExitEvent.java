package com.loficostudios.floralcraftapi.world.event;

import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FloralWorldExitEvent extends FloralWorldEvent {
    private final Player player;
    private final WorldExitReason reason;
    private final FloralWorld from;

    public FloralWorldExitEvent(FloralWorld world, FloralWorld from, Player player, WorldExitReason reason) {
        super(world);
        this.player = player;
        this.reason = reason;
        this.from = from;
    }
    public WorldExitReason getReason() {
        return reason;
    }

    @Nullable public FloralWorld from() {
        return from;
    }
    public Player getPlayer() {
        return player;
    }
}
