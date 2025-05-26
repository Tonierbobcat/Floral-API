package com.loficostudios.floralcraftapi.world.entity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface FloralEntity {
    LivingEntity getBukkitEntity();

    @NotNull PersistentDataContainer getPersistentDataContainer();

    @NotNull UUID getUniqueId();

    @NotNull String getName();

    @NotNull Location getLocation();

    default int entityId() {
        return getBukkitEntity().getEntityId();
    }

    boolean teleport(@NotNull Location location);

    boolean isDead();
}
