package com.loficostudios.floralcraftapi.dungeon;

import com.loficostudios.floralcraftapi.utils.BlockLocation;
import org.bukkit.entity.Player;

public interface DungeonChest {
    LootTable getLootTable();
    boolean isLocked(Player player);
    BlockLocation getLocation();
    DungeonChestOpenResult open(Player player);
}
