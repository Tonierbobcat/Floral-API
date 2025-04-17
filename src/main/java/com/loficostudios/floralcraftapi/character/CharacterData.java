package com.loficostudios.floralcraftapi.character;

import com.loficostudios.floralcraftapi.npc.MinecraftSkin;
import com.loficostudios.floralcraftapi.utils.IdentifiableObject;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CharacterData extends IdentifiableObject<String> {
    private final String name;
    private final StatModifier[] additionalBuffs;
    private final String mythic;
    private final CharacterClass characterClass;

    private final MinecraftSkin model;

    public CharacterData(String id, String name, StatModifier[] additionalBuffs, String mythic, CharacterClass characterClass, @Nullable MinecraftSkin model) {
        super(id);
        this.name = name;
        this.additionalBuffs = additionalBuffs;
        this.mythic = mythic;
        this.characterClass = characterClass;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public StatModifier[] getAdditionalBuffs() {
        return additionalBuffs;
    }

    public Optional<MinecraftSkin> getModel() {
        return Optional.ofNullable(model);
    }

    public String getMythic() {
        return mythic;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }
}
