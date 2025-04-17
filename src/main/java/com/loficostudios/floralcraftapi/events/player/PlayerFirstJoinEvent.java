package com.loficostudios.floralcraftapi.events.player;

import org.bukkit.entity.Player;

public class PlayerFirstJoinEvent extends PlayerEvent {
    public PlayerFirstJoinEvent(Player player) {
        super(player);
    }
}
