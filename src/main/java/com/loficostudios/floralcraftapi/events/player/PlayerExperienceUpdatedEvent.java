package com.loficostudios.floralcraftapi.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerExperienceUpdatedEvent extends PlayerEvent {
    public PlayerExperienceUpdatedEvent(Player player) {
        super(player);
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
