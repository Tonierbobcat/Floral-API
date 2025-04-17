package com.loficostudios.floralcraftapi.world.entity;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.npc.NPCData;
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

    boolean teleport(@NotNull Location location);

    boolean isDead();

    default void remove() {
        FloralCraftAPI.getConfig().getNPCProvider().remove(this);
    }

    static FloralEntity spawn(NPCData data, Location location) {
        return FloralCraftAPI.getConfig().getNPCProvider().spawn(data, location);
    }
}
