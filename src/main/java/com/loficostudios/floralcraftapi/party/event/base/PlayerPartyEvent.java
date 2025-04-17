package com.loficostudios.floralcraftapi.party.event.base;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.player.PartyEntity;

public abstract class PlayerPartyEvent extends PartyEvent {
    private final PartyEntity player;

    public PlayerPartyEvent(Party party, PartyEntity player) {
        super(party);
        this.player = player;
    }

    public PartyEntity getPlayer() {
        return player;
    }
}
