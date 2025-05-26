package com.loficostudios.floralcraftapi.floor.portal;

import com.loficostudios.floralcraftapi.dungeon.DungeonTemplate;
import com.loficostudios.floralcraftapi.utils.BlockLocation;
import com.loficostudios.floralcraftapi.utils.Bounds;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class DungeonPortal {
    private final DungeonTemplate dungeon;
    private final Bounds bounds;
    private final BiConsumer<Player, DungeonPortal> onEnter;
    public DungeonPortal(DungeonTemplate dungeon, BlockLocation min, BlockLocation max, BiConsumer<Player, DungeonPortal> onEnter) {
        this.dungeon = dungeon;
        this.bounds = new Bounds(min, max);
        this.onEnter = onEnter;
    }

    public void enter(Player player) {
        if (onEnter != null)
            onEnter.accept(player, this);
    }

    public DungeonTemplate getDungeon() {
        return dungeon;
    }

    public Bounds getBounds() {
        return bounds;
    }
}
