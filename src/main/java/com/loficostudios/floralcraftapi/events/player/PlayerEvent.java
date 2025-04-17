package com.loficostudios.floralcraftapi.events.player;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final FloralPlayer player;

    public PlayerEvent(Player player) {
        this.player = new FloralPlayer(player);
    }

    public FloralPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
