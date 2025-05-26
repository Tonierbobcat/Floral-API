package com.loficostudios.floralcraftapi.character.entity;

import com.loficostudios.floralcraftapi.character.CharacterInstance;
import com.loficostudios.floralcraftapi.party.player.PartyMember;

public interface CharacterEntity extends PartyMember {
    CharacterInstance getCharacterInstance();
    void setCharacterInstance(CharacterInstance character);

    void setLevel(int level);
}
