package com.loficostudios.floralcraftapi.party.event.base;

import com.loficostudios.floralcraftapi.party.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class PartyEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Party party;

    public PartyEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
