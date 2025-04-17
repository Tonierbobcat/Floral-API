package com.loficostudios.floralcraftapi.party;

import com.loficostudios.floralcraftapi.profile.components.mail.Mail;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PartyInvite extends Mail {
    private final Party party;
    public PartyInvite(Party party, OfflinePlayer sender) {
        super(sender, sender.getName() + " has send you an invite to their party!");
        this.party = party;
    }

    @Override
    public void receive(Player player) {
        if (party != null && !party.isEmpty()) {
            party.acceptInvite(this, player);
        }
    }

    public Party getParty() {
        return party;
    }
}
