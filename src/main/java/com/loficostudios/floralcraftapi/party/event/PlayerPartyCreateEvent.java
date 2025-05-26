package com.loficostudios.floralcraftapi.party.event;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PlayerPartyEvent;
import com.loficostudios.floralcraftapi.party.player.PartyMember;

public class PlayerPartyCreateEvent extends PlayerPartyEvent {
    private final boolean anonymous;
    public PlayerPartyCreateEvent(Party party, PartyMember player, boolean anonymous) {
        super(party, player);
        this.anonymous = anonymous;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
}
