package com.loficostudios.floralcraftapi.party.event.player;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.PartyInvitePlayerResult;
import com.loficostudios.floralcraftapi.party.event.base.PartyEvent;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;

public class PlayerPartyInviteEvent extends PartyEvent {
    private final FloralPlayer sender;
    private final FloralPlayer receiver;
    private final PartyInvitePlayerResult result;

    public PlayerPartyInviteEvent(Party party, PartyInvitePlayerResult result, Player sender, Player receiver) {
        super(party);
        this.sender = new FloralPlayer(sender);
        this.receiver = new FloralPlayer(receiver);
        this.result = result;
    }

    public PartyInvitePlayerResult getResult() {
        return result;
    }

    public FloralPlayer getSender() {
        return sender;
    }

    public FloralPlayer getReceiver() {
        return receiver;
    }
}
