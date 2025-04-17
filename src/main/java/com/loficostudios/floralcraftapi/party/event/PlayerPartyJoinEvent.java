package com.loficostudios.floralcraftapi.party.event;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PlayerPartyEvent;
import com.loficostudios.floralcraftapi.party.player.PartyEntity;

public class PlayerPartyJoinEvent extends PlayerPartyEvent {
    public PlayerPartyJoinEvent(Party party, PartyEntity player) {
        super(party, player);
    }
}
