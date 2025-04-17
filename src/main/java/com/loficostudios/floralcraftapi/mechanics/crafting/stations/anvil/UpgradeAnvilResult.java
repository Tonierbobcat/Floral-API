package com.loficostudios.floralcraftapi.mechanics.crafting.stations.anvil;

import org.bukkit.inventory.ItemStack;

public record UpgradeAnvilResult(ItemStack item, Type type) {

    public enum Type {
        NOT_UPGRADABLE,
        NOT_ENOUGH_RESOURCES,
        NOT_ENOUGH_ESSENCE,
        SUCCESS,
        FAILURE,
    }
}
