package com.loficostudios.floralcraftapi.events.player;

import org.bukkit.entity.Player;

/**
 * Triggers every time a player levels up
 */
public class PlayerLevelUpEvent extends PlayerEvent {
    private final int levels;
    public PlayerLevelUpEvent(Player player, int levels) {
        super(player);
        this.levels = levels;
    }
    public int getLevels() {
        return levels;
    }
}
