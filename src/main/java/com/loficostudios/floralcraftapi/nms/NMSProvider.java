package com.loficostudios.floralcraftapi.nms;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NMSProvider {
    void applyCamera(Player viewer, int targetId);

    void teleport(Collection<? extends Player> viewers, Location location, int entityID);

    void spawnDummy(Collection<? extends Player> viewers, Location location, int entityID);
    int spawnDummy(Collection<? extends Player> viewers, Location location);

    void spawnFakeEntity(Collection<? extends Player> viewers, int entityID, UUID uuid, EntityType entityType, org.bukkit.Location location);

    void setEntityDisplay(Collection<? extends Player> viewers, int entity, @NotNull Component text);

    int[] setNameTag(Collection<? extends Player> viewers, Entity entity, List<? extends Component> lines);

    void removeNameTag(Collection<? extends Player> viewers, int entity);

    public void removeEntities(Collection<? extends Player> viewers, int... entities);

    void walkTo(LivingEntity bukkitEntity, Location ownerLocation);

    void setPassenger(Collection<? extends Player> viewers, int entity, int... passenger);
}
