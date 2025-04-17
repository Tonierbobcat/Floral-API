package com.loficostudios.floralcraftapi.events.player;

import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import org.bukkit.entity.Player;

public abstract class PlayerResourceEvent extends PlayerEvent {

    private final Resource resource;

    public PlayerResourceEvent(Player player, Resource resource) {
        super(player);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
