package com.loficostudios.floralcraftapi.party.player;

import com.loficostudios.floralcraftapi.party.Party;
import org.jetbrains.annotations.Nullable;

public interface PartyHolder {
    @Nullable Party getCurrentParty();

    /**
     *
     * @return the previous party
     */
    @Nullable Party setCurrentParty(@Nullable Party party);
}
