package com.loficostudios.floralcraftapi.profile;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface IUserData extends DataContainer {
    OfflinePlayer getHolder();
    UUID getUniqueId();
}
