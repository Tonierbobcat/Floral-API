package com.loficostudios.floralcraftapi.profile.components.base;

import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.utils.interfaces.Component;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link OptionalProfileComponent}
 */
@Deprecated (forRemoval = true)
public abstract class ProfileComponent implements Component<@NotNull ProfileData> {
    private final ProfileData profile;

    public ProfileComponent(@NotNull ProfileData profile) {
        this.profile = profile;
    }

    @Override
    public @NotNull ProfileData getParent() {
        return profile;
    }
}
