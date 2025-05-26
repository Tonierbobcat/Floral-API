package com.loficostudios.floralcraftapi.events.player;

import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;


public class PlayerResourceChangedEvent extends PlayerResourceEvent {
    private final double newValue;
    private final double oldValue;
    public PlayerResourceChangedEvent(Player player, Resource resource, double newValue, double oldValue) {
        super(player, resource);
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    public double getNewValue() {
        return newValue;
    }

    public double getOldValue() {
        return oldValue;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
