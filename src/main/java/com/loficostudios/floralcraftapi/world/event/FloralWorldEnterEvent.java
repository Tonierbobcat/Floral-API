package com.loficostudios.floralcraftapi.world.event;

import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FloralWorldEnterEvent extends FloralWorldEvent {
    private final Player player;
    private final WorldEnterReason reason;
    private final FloralWorld from;
    public FloralWorldEnterEvent(FloralWorld world, FloralWorld from, Player player, WorldEnterReason reason) {
        super(world);
        this.player = player;
        this.reason = reason;
        this.from = from;

    }
    public WorldEnterReason getReason() {
        return reason;
    }

    @Nullable public FloralWorld from() {
        return from;
    }

    public Player getPlayer() {
        return player;
    }
}
