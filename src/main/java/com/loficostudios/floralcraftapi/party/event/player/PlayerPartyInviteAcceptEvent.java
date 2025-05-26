package com.loficostudios.floralcraftapi.party.event.player;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PartyEvent;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerPartyInviteAcceptEvent extends PartyEvent {

    private final OfflinePlayer sender;
    private final FloralPlayer receiver;

    public PlayerPartyInviteAcceptEvent(Party party, OfflinePlayer sender, Player receiver) {
        super(party);
        this.sender = sender;
        this.receiver = FloralPlayer.get(receiver);
    }

    public OfflinePlayer getSender() {
        return sender;
    }

    public FloralPlayer getWhoAccepted() {
        return receiver;
    }

}
