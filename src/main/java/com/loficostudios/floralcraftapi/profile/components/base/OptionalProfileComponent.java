package com.loficostudios.floralcraftapi.profile.components.base;

import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.utils.interfaces.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class OptionalProfileComponent implements Component<Optional<ProfileData>> {
    private final ProfileData profile;

    public OptionalProfileComponent(@Nullable ProfileData profile) {
        this.profile = profile;
    }

    @Override
    public Optional<ProfileData> getParent() {
        return Optional.ofNullable(profile);
    }
}
