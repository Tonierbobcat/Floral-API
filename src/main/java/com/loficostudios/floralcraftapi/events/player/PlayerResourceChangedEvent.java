package com.loficostudios.floralcraftapi.events.player;

import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import org.bukkit.entity.Player;


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
}
