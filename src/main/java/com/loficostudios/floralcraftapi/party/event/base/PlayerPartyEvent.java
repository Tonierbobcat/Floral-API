package com.loficostudios.floralcraftapi.party.event.base;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.player.PartyMember;

public abstract class PlayerPartyEvent extends PartyEvent {
    private final PartyMember player;

    public PlayerPartyEvent(Party party, PartyMember player) {
        super(party);
        this.player = player;
    }

    public PartyMember getPlayer() {
        return player;
    }
}
