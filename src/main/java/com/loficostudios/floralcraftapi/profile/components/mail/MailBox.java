package com.loficostudios.floralcraftapi.profile.components.mail;

import com.loficostudios.floralcraftapi.party.PartyInvite;
import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.profile.components.base.ProfileComponent;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MailBox extends ProfileComponent {
    private final List<Mail> inbox = new ArrayList<>();

    public MailBox(PlayerProfile profile) {
        super(profile);
    }

    public void put(OfflinePlayer sender, Mail mail, boolean silent) {
        inbox.add(mail);
    }

    public void put(OfflinePlayer sender, Mail mail) {
        put(sender,mail, false);
    }

    public Collection<PartyInvite> getPartyInvites() {
        var result = new ArrayList<PartyInvite>();
        for (Mail mail : new ArrayList<>(inbox)) {
            if (mail instanceof PartyInvite)
                result.add(((PartyInvite) mail));
        }
        return Collections.unmodifiableList(result);
    }

    public void remove(Mail mail) {
        inbox.remove(mail);
    }
}
