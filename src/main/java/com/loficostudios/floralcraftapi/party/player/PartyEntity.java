package com.loficostudios.floralcraftapi.party.player;

import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.world.entity.FloralEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface PartyEntity extends FloralEntity, PartyHolder {
    Optional<Player> getPlayer();

    int getLevel();

    @Nullable Party setCurrentParty(@Nullable Party party);

    @Nullable Party getCurrentParty();
}
