package com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public record UpgradeWeaponResult(@Nullable ItemStack item, Type type) {

    public boolean isSuccess() {
        return type.isSuccess();
    }

    public enum Type {

        SUCCESS,

        NOT_MMOITEM,

        NO_UPGRADE_TEMPLATE,

        @Deprecated
        MAX_LEVEL,

        FAILURE,

        ERROR;

        public boolean isSuccess() {
            return this.equals(Type.SUCCESS);
        }
    }
}
