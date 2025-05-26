package com.loficostudios.floralcraftapi.party.event;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PlayerPartyEvent;
import com.loficostudios.floralcraftapi.party.player.PartyMember;

public class PlayerPartyLeaveEvent extends PlayerPartyEvent {

    private final boolean isOwner;

    public PlayerPartyLeaveEvent(Party party, PartyMember player, boolean isOwner) {
        super(party, player);
        this.isOwner = isOwner;
    }

    public boolean isOwner() {
        return isOwner;
    }
}
