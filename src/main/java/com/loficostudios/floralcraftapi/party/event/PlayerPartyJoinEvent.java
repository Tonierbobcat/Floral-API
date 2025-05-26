package com.loficostudios.floralcraftapi.party.event;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PlayerPartyEvent;
import com.loficostudios.floralcraftapi.party.player.PartyMember;

public class PlayerPartyJoinEvent extends PlayerPartyEvent {
    public PlayerPartyJoinEvent(Party party, PartyMember player) {
        super(party, player);
    }
}
