package com.loficostudios.floralcraftapi.character.entity;

import com.loficostudios.floralcraftapi.character.CharacterInstance;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class CharacterEntityManager {

    private final HashMap<UUID, List<CharacterEntity>> entities = new HashMap<>();

    public List<CharacterEntity> getEntities(UUID uuid) {
        return new ArrayList<>(entities.getOrDefault(uuid, List.of()));
    }

    ActiveMob spawn(CharacterInstance instance, Location location) {
        var character = instance.getCharacter();

        var mob = MythicBukkit.inst().getMobManager().getMythicMob(character.getMythic());
        Validate.isTrue(mob.isPresent(), character.getMythic() + " is not a valid MythicMob");

        var active = mob.get().spawn(BukkitAdapter.adapt(location), instance.getLevel());
        try {
            Validate.isTrue(active.getEntity().getBukkitEntity() instanceof LivingEntity);
        } catch (IllegalArgumentException e) {
            active.remove();
            throw e;
        }
        active.setDisplayName(instance.getCharacter().getName());

        return active;
    }

    void addEntity(CharacterEntity entity) {
        entities.compute(entity.getCharacterInstance().getUniqueId(), (uuid, entities) -> {
            if (entities != null) {
                entities.add(entity);
                return entities;
            }

            return new ArrayList<>(Collections.singleton(entity));
        });
    }
}
