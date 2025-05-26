package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.events.player.PlayerEvent;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerTravelEvent extends PlayerEvent implements Cancellable {

    private boolean cancelled;

    private final Destination destination;

    public PlayerTravelEvent(Player player, Destination destination) {
        super(player);

        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    private static final HandlerList HANDLERS = new HandlerList();
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
