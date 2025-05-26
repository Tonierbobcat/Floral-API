package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DungeonWorldEnterEvent extends DungeonEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final FloralPlayer player;

    public DungeonWorldEnterEvent(Dungeon dungeon, Player player) {
        super(dungeon);
        this.player = FloralPlayer.get(player);
    }

    public FloralPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
