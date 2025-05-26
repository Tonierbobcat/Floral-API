package com.loficostudios.floralcraftapi.events.player;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerEvent extends Event {
    private final FloralPlayer player;

    public PlayerEvent(Player player) {
        this.player = FloralPlayer.get(player);
    }

    public FloralPlayer getPlayer() {
        return player;
    }
}
