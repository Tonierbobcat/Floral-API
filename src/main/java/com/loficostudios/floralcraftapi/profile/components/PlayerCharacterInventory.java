package com.loficostudios.floralcraftapi.profile.components;

import com.loficostudios.floralcraftapi.character.CharacterData;
import com.loficostudios.floralcraftapi.character.CharacterInstance;
import com.loficostudios.floralcraftapi.profile.components.base.OptionalProfileComponent;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class PlayerCharacterInventory extends OptionalProfileComponent {
    private final HashMap<UUID, CharacterInstance> characters = new HashMap<>();
    private final HashMap<String, UUID> characterData = new HashMap<>();

    public PlayerCharacterInventory(@Nullable ProfileData profile) {
        super(profile);
    }

    public Collection<CharacterInstance> getCharacters() {
        return Collections.unmodifiableCollection(characters.values());
    }

    public boolean removeCharacter(String characterID) {
        UUID uuid = characterData.get(characterID);
        if (uuid == null)
            return false;

        characters.remove(uuid);
        characterData.remove(characterID);
        return true;
    }

    public boolean removeCharacter(CharacterData character) {
        return this.removeCharacter(character.getID());
    }

    public boolean addCharacter(CharacterData character, int level, int experience) {
        var uuid = UUID.randomUUID();
        var instance = new CharacterInstance(character, uuid, level, experience);
        if (characterData.containsKey(character.getID()))
            return false;
        characters.put(instance.getUniqueId(), instance);
        characterData.put(character.getID(), instance.getUniqueId());
        return true;
    }

    public boolean addCharacter(CharacterData character, int level) {
        return this.addCharacter(character, 1,0);
    }

    public boolean addCharacter(CharacterData character) {
        return addCharacter(character, 1, 0);
    }
}
