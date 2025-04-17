package com.loficostudios.floralcraftapi.events.player;

import org.bukkit.entity.Player;

public class PlayerExperienceUpdatedEvent extends PlayerEvent {
    public PlayerExperienceUpdatedEvent(Player player) {
        super(player);
    }
}
