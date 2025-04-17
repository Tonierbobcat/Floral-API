package com.loficostudios.floralcraftapi.party;

import com.loficostudios.floralcraftapi.character.entity.CharacterEntity;
import com.loficostudios.floralcraftapi.party.event.PartyPromotionEvent;
import com.loficostudios.floralcraftapi.party.event.PlayerPartyJoinEvent;
import com.loficostudios.floralcraftapi.party.event.PlayerPartyLeaveEvent;
import com.loficostudios.floralcraftapi.party.event.player.PlayerPartyInviteAcceptEvent;
import com.loficostudios.floralcraftapi.party.event.player.PlayerPartyInviteEvent;
import com.loficostudios.floralcraftapi.party.player.PartyEntity;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Party {
    Map<UUID, PartyEntity> members = new HashMap<>();
    private PartyEntity owner;
    private final String tag;
    private final UUID uuid;

    Party(PartyEntity owner) {
        this.tag = owner.getName().replace(" ", "_").toLowerCase() + "_" + System.currentTimeMillis();
        this.uuid = UUID.randomUUID();

        this.owner = owner;
        this.addMember(owner, false);
    }

    /**
     * Creates party anonymously so it will not trigger join events;
     */
    Party(List<PartyEntity> players, String tag) {
        Validate.isTrue(!players.isEmpty(), "Party must have at least one player");

        this.tag = tag;
        this.uuid = UUID.randomUUID();

        for (int i = 0; i < players.size(); i++) {
            var player = players.get(i);
            if (i == 0) {
                this.owner = player;
            }
            this.addMember(player, false);
        }
    }

    public StatModifier[] getBuffs() {
        var members = new ArrayList<>(getMembers());

        List<StatModifier> result = new ArrayList<>();
        var characters = members.stream().map(p -> {
            if (p instanceof CharacterEntity ent) {
                return ent;
            } else {
                return null;
            }
        }).filter(Objects::nonNull).toList();
        for (CharacterEntity character : characters) {
            var instance = character.getCharacterInstance();
            result.addAll(instance.getAppliedAdditionalBuffs());
        }

        return result.toArray(StatModifier[]::new);
    }

    public Collection<PartyEntity> getPlayersAlive() {
        return members.values().stream().filter(p -> !p.isDead()).toList();
    }

    public Collection<PartyEntity> getMembers() {
        return Collections.unmodifiableCollection(members.values());
    }

    public String getTag() {
        return tag;
    }

    public PartyEntity getOwner() {
        return owner;
    }

    public PartyInvitePlayerResult invite(Player sender, Player receiver) {
        if (sender.getUniqueId().equals(receiver.getUniqueId())) {
            return handlePartyInviteResult(PartyInvitePlayerResult.SELF, sender, receiver);
        }

        var party = new FloralPlayer(sender).getCurrentParty();
        if (party != null && party.isMember(receiver.getUniqueId())) {
            return handlePartyInviteResult(PartyInvitePlayerResult.ALREADY_IN_PARTY, sender, receiver);
        }

        new FloralPlayer(receiver).getMail().put(sender, new PartyInvite(this, sender), false);
        return handlePartyInviteResult(PartyInvitePlayerResult.SUCCESS, sender, receiver);
    }

    private PartyInvitePlayerResult handlePartyInviteResult(PartyInvitePlayerResult result, Player sender, Player receiver) {
        var event = new PlayerPartyInviteEvent(this, result, sender, receiver);
        Bukkit.getPluginManager().callEvent(event);
        return result;
    }

    public boolean isOwner(Player player) {
        return isOwner(player.getUniqueId());
    }
    public boolean isOwner(PartyEntity player) {
        return isOwner(player.getUniqueId());
    }
    public boolean isOwner(UUID uuid) {
        return uuid.equals(owner.getUniqueId());
    }
    public boolean isMember(PartyEntity player) {
        return isMember(player.getUniqueId());
    }
    public boolean isMember(UUID uuid) {
        return members.containsKey(uuid);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    private void addMember(PartyEntity player, boolean event) {
        var isAlreadyInParty = members.put(player.getUniqueId(), player) != null;
        if (isAlreadyInParty) {
            Debug.logWarning("Cannot add member " + player.getName() + " because player is already in party!");
            return;
        }

        var last = player.setCurrentParty(this);
        if (last != null)
            last.leave(player);
        if (!event)
            return;
        var e = new PlayerPartyJoinEvent(this, player);
        Bukkit.getPluginManager().callEvent(e);
    }

    private void removeMember(PartyEntity player, boolean event) {
        Debug.log(player.getName() + " is leaving party... " + tag);
        var wasOwner = isOwner(player);
        members.remove(player.getUniqueId());

        var current = player.getCurrentParty();
        if (current != null && current.uuid.equals(this.uuid))
            player.setCurrentParty(null);

        if (!members.isEmpty()) {
            if (wasOwner) {
                PartyEntity owner = new ArrayList<>(members.values()).getFirst();
                var e = new PartyPromotionEvent(this, owner, null, PartyPromotionEvent.PromotionType.OWNER);
                Bukkit.getPluginManager().callEvent(e);
                Debug.log("New Owner: " + owner.getName());
                this.owner = owner;
            }
        } else {
            Debug.log("Disbanding party...");
            disband();
        }
        if (event) {
            var e = new PlayerPartyLeaveEvent(this, player, wasOwner);
            Bukkit.getPluginManager().callEvent(e);
        }
    }

    public void join(PartyEntity player) {
        this.addMember(player, true);
    }

    public void leave(UUID uuid) {
        leave(members.get(uuid));
    }

    public void leave(PartyEntity player) {
        if (player == null) {
            Debug.logWarning("Party#leave(Player) - Player is null");
            return;
        }
        if (!members.containsKey(player.getUniqueId())) {
            Debug.logWarning("Party#leave(Player) - UUID not found in party members");
            return;
        }
        removeMember(player, true);
    }

    public void disband() {
        var members = new ArrayList<>(this.members.values());
        for (PartyEntity member : members) {
            this.members.remove(member.getUniqueId());
            member.setCurrentParty(null);

            member.getPlayer().ifPresent((player) -> player.sendMessage(Component.text("Party was disbanded!") //todo create party disbanded event
                    .color(TextColor.color(255, 0, 0))));
        }
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public void acceptInvite(PartyInvite invite, Player player) {
        if (invite == null || isMember(player.getUniqueId())) {
            return;
        }
        var p = new FloralPlayer(player);
        p.getMail().remove(invite);

        if (p.getCurrentParty() != null) {
            p.getCurrentParty()
                    .leave(p);
        }
        var event = new PlayerPartyInviteAcceptEvent(this, invite.getSender(), player);
        Bukkit.getPluginManager().callEvent(event);
        join(p);
    }

    public @Nullable PartyEntity getMember(LivingEntity entity) {
        return members.get(entity.getUniqueId());
    }
    public @Nullable PartyEntity getMember(Player player) {
        return getMember(player);
    }
}
