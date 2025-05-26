package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import com.loficostudios.floralcraftapi.dungeon.room.DungeonRoom;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DungeonRoomExitEvent extends DungeonEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final  LivingEntity entity;
    private final @Nullable DungeonRoom to;
    private final @NotNull DungeonRoom from;
    private boolean cancelled;

    public DungeonRoomExitEvent(Dungeon dungeon,  LivingEntity entity, @Nullable DungeonRoom dungeonRoom, @NotNull DungeonRoom from) {
        super(dungeon);
        this.entity = entity;
        to = dungeonRoom;
        this.from = from;
    }

    public boolean isPlayer() {
        return entity instanceof Player;
    }

    public @NotNull DungeonRoom getFrom() {
        return from;
    }

    public @Nullable DungeonRoom getTo() {
        return to;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
