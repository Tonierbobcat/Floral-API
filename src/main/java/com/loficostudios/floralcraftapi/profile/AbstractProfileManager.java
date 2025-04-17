package com.loficostudios.floralcraftapi.profile;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractProfileManager<Impl> {
    public abstract Impl createProfile(OfflinePlayer player);
    public abstract Impl getProfile(OfflinePlayer player);
    public abstract void saveProfile(Impl data);
    public abstract void initialize(Consumer<Map<UUID, Impl>> onLoad);
    public abstract void saveProfile(@NotNull OfflinePlayer player);
    public abstract boolean isLoaded();

    public abstract Collection<Impl> getProfiles();
}
