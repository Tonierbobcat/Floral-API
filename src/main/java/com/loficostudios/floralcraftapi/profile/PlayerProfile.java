package com.loficostudios.floralcraftapi.profile;

import com.loficostudios.floralcraftapi.profile.components.PlayerResources;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PlayerProfile extends DataContainer {
    @NotNull PlayerResources getResources();

    @NotNull UUID getUniqueId();

    @NotNull OfflinePlayer getHolder();

    @NotNull MMOPlayerData getMMO();
}
