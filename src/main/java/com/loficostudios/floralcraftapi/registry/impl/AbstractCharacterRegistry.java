package com.loficostudios.floralcraftapi.registry.impl;

import com.loficostudios.floralcraftapi.character.CharacterData;
import com.loficostudios.floralcraftapi.registry.Registry;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public abstract class AbstractCharacterRegistry implements Registry<String, CharacterData> {
    private final HashMap<String, CharacterData> characters = new HashMap<>();

    @Override
    public @Nullable CharacterData get(String id) {
        return characters.get(id);
    }

    @Override
    public boolean register(CharacterData object) {
        Validate.isTrue(object != null, "Character cannot be null");
        Validate.isTrue(!characters.containsKey(object.getId()), "Character with ID: " + object.getId() + " is already registered");
        characters.put(object.getId(), object);
        return true;
    }

    @Override
    public Collection<CharacterData> getRegistered() {
        return Collections.unmodifiableCollection(characters.values());
    }
}
