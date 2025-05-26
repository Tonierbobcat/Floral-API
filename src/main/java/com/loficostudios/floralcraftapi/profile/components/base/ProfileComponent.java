package com.loficostudios.floralcraftapi.profile.components.base;

import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.utils.interfaces.Component;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link OptionalProfileComponent}
 */
@Deprecated (forRemoval = true)
public abstract class ProfileComponent implements Component<@NotNull PlayerProfile> {
    private final PlayerProfile profile;

    public ProfileComponent(@NotNull PlayerProfile profile) {
        this.profile = profile;
    }

    @Override
    public @NotNull PlayerProfile getParent() {
        return profile;
    }
}
