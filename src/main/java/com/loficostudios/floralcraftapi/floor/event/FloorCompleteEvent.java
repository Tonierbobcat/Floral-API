package com.loficostudios.floralcraftapi.floor.event;

import com.loficostudios.floralcraftapi.floor.Floor;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FloorCompleteEvent extends FloorEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final FloralPlayer player;

    public FloorCompleteEvent(Floor floor, Player player) {
        super(floor);
        this.player = FloralPlayer.get(player);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public FloralPlayer getPlayer() {
        return player;
    }
}
