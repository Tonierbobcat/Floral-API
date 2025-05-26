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

public class DungeonRoomEnterEvent extends DungeonEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final LivingEntity entity;

    private final @NotNull DungeonRoom to;
    private final @Nullable DungeonRoom from;

    private boolean cancelled;

    public DungeonRoomEnterEvent(Dungeon dungeon, LivingEntity entity, @NotNull DungeonRoom to, @Nullable DungeonRoom from) {
        super(dungeon);
        this.entity = entity;
        this.to = to;
        this.from = from;
    }

    public @Nullable DungeonRoom getFrom() {
        return from;
    }

    public @NotNull DungeonRoom getTo() {
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

    public boolean isPlayer() {
        return entity instanceof Player;
    }
}
