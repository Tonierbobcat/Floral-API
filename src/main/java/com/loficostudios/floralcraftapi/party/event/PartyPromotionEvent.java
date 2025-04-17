package com.loficostudios.floralcraftapi.party.event;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.event.base.PlayerPartyEvent;
import com.loficostudios.floralcraftapi.party.player.PartyEntity;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PartyPromotionEvent extends PlayerPartyEvent {
    private final @Nullable FloralPlayer whoPromoted;
    private final PromotionType type;
    public PartyPromotionEvent(Party party, PartyEntity player, @Nullable Player whoPromoted, PromotionType type) {
        super(party, player);
        this.whoPromoted = whoPromoted != null ? new FloralPlayer(whoPromoted) : null;
        this.type = type;
    }

    public @Nullable FloralPlayer getWhoPromoted() {
        return whoPromoted;
    }

    public enum PromotionType {
        OWNER,
        ADMIN
    }
}
