package com.loficostudios.floralcraftapi.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    private static final HandlerList HANDLERS = new HandlerList();
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
