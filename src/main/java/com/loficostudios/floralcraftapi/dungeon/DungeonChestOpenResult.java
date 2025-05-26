package com.loficostudios.floralcraftapi.dungeon;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public record DungeonChestOpenResult(Type type, Collection<? extends ItemStack> items) {
    public enum Type {
        LOCKED,
        ALREADY_OPENED,
        SUCCESS
    }
}
