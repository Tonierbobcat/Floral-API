package com.loficostudios.floralcraftapi.party;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.party.event.PlayerPartyCreateEvent;
import com.loficostudios.floralcraftapi.party.player.PartyMember;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PartyManager {
    private final HashMap<UUID,Party> parties = new HashMap<>();


    public PartyManager() {
        FloralCraftAPI.runTaskTimer(new BukkitRunnable() {
            private long elapsedTicks = 0L;
            @Override
            public void run() {
                parties.values().removeIf(p -> p.getPlayersAlive().isEmpty());

                for (Party party : getParties()) {
                    for (PartyMember player : party.getMembers()) {
                        var isNPC = player.getPlayer().isEmpty();
                        if (isNPC && elapsedTicks % 10 == 0)
                            handleBot(party, player, elapsedTicks);
                    }
                }

                elapsedTicks += 1;
            }
        }, 0, 1);
    }

    public Optional<ActiveMob> getActiveMob(PartyMember player) {
        return  MythicBukkit.inst().getMobManager().getActiveMob(player.getBukkitEntity().getUniqueId());
    }

    private void handleBot(Party party, PartyMember member, long elapsedTicks) {
        if (member.isDead())
            return;
        var opt = getActiveMob(member);
        if (opt.isEmpty())
            return;
        ActiveMob mythic = opt.get();
        var hasTarget = hasTarget(mythic);

        if (elapsedTicks % 10 == 0) {
            var owner = party.getOwner();
            var playerPos = member.getBukkitEntity().getLocation();

            if (!party.isOwner(member) && !owner.isDead()) { // teleport to owner if outside 32 block range and has no target
                var pos = owner.getBukkitEntity().getLocation();
                var distance = playerPos.distance(pos);
                if (!hasTarget && distance > 32.0) {
                    Debug.log("Teleporting to owner...");
                    member.teleport(pos);
                }
            }
        }

        if (elapsedTicks % 40 == 0) {
            if (!hasTarget)
                handleNoTarget(member, mythic, party);
        }
    }

    private boolean hasTarget(ActiveMob entity) {
        return entity.hasTarget();
    }

    private void handleNoTarget(PartyMember member, ActiveMob playerEntity, Party party) { //set target of player to owners target if they have one else have the player follower the owner
        if (party.isOwner(member))
            return;
        var owner = party.getOwner();
        if (owner == null || owner.isDead())
            return;

        var optEntity = getActiveMob(owner);
        if (optEntity.isEmpty()) return;
        var ownerEntity = optEntity.get();

        var optTarget = Optional.ofNullable(ownerEntity.getEntity().getTarget());
        optTarget.ifPresent(playerEntity::setTarget);

    }

    public Collection<Party> getParties() {
        return Collections.unmodifiableCollection(parties.values());
    }

    private void addParty(Party party) {
        parties.put(party.getUniqueId(), party);
    }

    private @Nullable Party getPartyFromPlayer(UUID uuid) {
        var parties = new ArrayList<>(this.parties.values());
        for (Party party : parties) {
            if (party.isMember(uuid))
                return party;
        }
        return null;
    }

    public @Nullable Party getPartyFromPlayer(Player player) {
        return getPartyFromEntity(player);
    }

    public @Nullable Party getPartyFromEntity(Entity entity) {
        var parties = new ArrayList<>(this.parties.values());
        for (Party party : parties) {
            if (party.isMember(entity.getUniqueId()))
                return party;
        }
        return null;
    }

    public @Nullable Party getParty(UUID uuid) {
        return parties.get(uuid);
    }

    public Party createAnonymousParty(ArrayList<PartyMember> members, String tag) {
        var party = new Party(members, tag);
        this.addParty(party);
        var event = new PlayerPartyCreateEvent(party, members.getFirst(), true);
        Bukkit.getPluginManager().callEvent(event);
        return party;
    }

    public Party createParty(Player player) {
        var p = FloralPlayer.get(player);
        var party = new Party(p);
        this.addParty(party);

        var event = new PlayerPartyCreateEvent(party, p, false);
        Bukkit.getPluginManager().callEvent(event);

        return party;
    }
}
