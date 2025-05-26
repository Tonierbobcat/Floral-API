package com.loficostudios.floralcraftapi.profile.components.base;

import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.utils.interfaces.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class OptionalProfileComponent implements Component<Optional<PlayerProfile>> {
    private final PlayerProfile profile;

    public OptionalProfileComponent(@Nullable PlayerProfile profile) {
        this.profile = profile;
    }

    @Override
    public Optional<PlayerProfile> getParent() {
        return Optional.ofNullable(profile);
    }
}
