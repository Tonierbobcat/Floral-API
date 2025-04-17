package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.events.player.PlayerEvent;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class PlayerTravelEvent extends PlayerEvent implements Cancellable {

    private boolean cancelled;

    public DestinationType getDestinationType() {
        return destinationType;
    }

    private final DestinationType destinationType;
    private final Location destination;

    public PlayerTravelEvent(Player player, FloralWorld destination) {
        super(player);
        this.destinationType = DestinationType.WORLD;
        this.destination = destination.getSpawnLocation();
    }
    public PlayerTravelEvent(Player player, Location destination) {
        super(player);
        this.destinationType = DestinationType.LOCATION;
        this.destination = destination;
    }

    public Location getDestination() {
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

    public enum DestinationType {
        LOCATION,
        WORLD
    }
}
