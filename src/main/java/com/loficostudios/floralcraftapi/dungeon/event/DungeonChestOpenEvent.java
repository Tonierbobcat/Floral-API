package com.loficostudios.floralcraftapi.dungeon.event;

import com.loficostudios.floralcraftapi.dungeon.Dungeon;
import com.loficostudios.floralcraftapi.dungeon.DungeonChest;
import com.loficostudios.floralcraftapi.dungeon.DungeonChestOpenResult;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.utils.BlockLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DungeonChestOpenEvent extends DungeonEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final FloralPlayer player;
    private final DungeonChest chest;
    private final DungeonChestOpenResult result;
    private final boolean wasLocked;

    public DungeonChestOpenEvent(Dungeon dungeon, Player player, DungeonChest chest, DungeonChestOpenResult result, boolean wasLocked) {
        super(dungeon);
        this.player = FloralPlayer.get(player);
        this.chest = chest;
        this.result = result;

        this.wasLocked = wasLocked;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public FloralPlayer getPlayer() {
        return player;
    }

    public DungeonChest getChest() {
        return chest;
    }

    public DungeonChestOpenResult getResult() {
        return result;
    }
    public boolean wasLocked() {
        return wasLocked;
    }
}
