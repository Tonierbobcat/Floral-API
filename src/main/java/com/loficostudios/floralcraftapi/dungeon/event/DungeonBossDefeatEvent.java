package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import com.loficostudios.floralcraftapi.dungeon.DungeonBoss;
import com.loficostudios.floralcraftapi.dungeon.room.DungeonRoom;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DungeonBossDefeatEvent extends DungeonEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final DungeonBoss boss;
    private final DungeonRoom room;
    private final FloralPlayer player;
    public DungeonBossDefeatEvent(Dungeon dungeon, DungeonBoss boss, @Nullable DungeonRoom room, @Nullable Player killer) { //todo maybe?? change this to living entity
        super(dungeon);
        this.boss = boss;
        this.room = room;
        this.player = killer != null ? FloralPlayer.get(killer) : null;
    }

    public @Nullable FloralPlayer getKiller() {
        return player;
    }

    @Override
    public Dungeon getDungeon() {
        return super.getDungeon();
    }

    public @NotNull DungeonBoss getBoss() {
        return boss;
    }

    public @Nullable DungeonRoom getRoom() {
        return room;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static final HandlerList getHandlerList() {
        return HANDLERS;
    }
}
