package com.loficostudios.floralcraftapi.party.player;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.party.Party;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class EntityPartyPlayer implements PartyEntity {
    private Party currentParty;
    private final LivingEntity entity;

    protected EntityPartyPlayer(Entity entity) {
        Validate.isTrue(entity != null, "Entity cannot be null");
        Validate.isTrue(entity instanceof LivingEntity, "Entity is not living");
        this.entity = ((LivingEntity) entity);
    }

    @Override
    public boolean isDead() {
        return entity.isDead();
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return entity;
    }

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return  entity.getPersistentDataContainer();
    }

    @Override
    public @NotNull String getName() {
        return entity.getName();
    }

    @Override
    public @NotNull Location getLocation() {
        return  entity.getLocation();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return entity.getUniqueId();
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.empty();
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        return entity.teleport(location);
    }

    @Override
    public @Nullable Party setCurrentParty(@Nullable Party party) {
        Validate.isTrue(party == null ||  party.isMember(this), "Player is not member!");

        var last = currentParty;
        currentParty = party;

        var pdc = entity.getPersistentDataContainer();
        if (party != null) {
            pdc.set(FloralCraftAPI.getNamespaceKey("party"), PersistentDataType.STRING, party.getUniqueId().toString());
        } else {
            pdc.remove(FloralCraftAPI.getNamespaceKey("party"));
        }

        return last;
    }

    @Override
    public @Nullable Party getCurrentParty() {
        return currentParty;
    }
}
