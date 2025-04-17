package com.loficostudios.floralcraftapi.character;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.character.entity.CharacterEntity;
import com.loficostudios.floralcraftapi.profile.components.holders.LevelHolder;
import com.loficostudios.floralcraftapi.profile.components.level.PlayerLevelManager;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class CharacterInstance implements LevelHolder {
    private final CharacterData character;
    private final PlayerLevelManager level;
    private final UUID uuid;
    private int quality;
    public CharacterInstance(CharacterData character) {
        this(character, UUID.randomUUID(), 1,0);
    }

    public CharacterInstance(CharacterData character, int level) {
        this(character, UUID.randomUUID(), level,0);
    }

    public CharacterInstance(CharacterData character, UUID uuid, int level, int experience) {
        this.character = character;
        this.uuid = uuid;
        this.level = new PlayerLevelManager(null, FloralCraftAPI.getConfig().characterLeveling(), this::onLevelChanged, level, experience);
    }

    @SuppressWarnings("deprecation")
    private void onLevelChanged(int level) {
        for (CharacterEntity entity : FloralCraftAPI.inst().getCharacterEntityManager().getEntities(uuid)) {
            entity.setLevel(level);
        }
    }

    public Collection<StatModifier> getAppliedAdditionalBuffs() {
        var level = getLevel();
        var result = new ArrayList<StatModifier>();
        var key = character.getID().toUpperCase();
        for (StatModifier modifier : new ArrayList<>(Arrays.stream(character.getAdditionalBuffs()).toList())) {
            modifier.getStat();
            Validate.isTrue(!modifier.getStat().isEmpty());
            result.add(new StatModifier(UUID.randomUUID(), key + "_" + modifier.getStat().toUpperCase(),
                    modifier.getStat(),
                    (modifier.getValue()), // todo change this
                    modifier.getType(),
                    modifier.getSlot(),
                    modifier.getSource()));
        }
        return result;
    }

    public CharacterData getCharacter() {
        return character;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public int getLevel() {
        return level.current();
    }

    public void setLevel(int level) {
        this.level.setLevel(Math.max(1, level));
    }

    @Override
    @Deprecated
    public @NotNull PlayerLevelManager getLevels() {
        return level;
    }
}
