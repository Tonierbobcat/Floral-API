package com.loficostudios.floralcraftapi.profile.components.holders;

import com.loficostudios.floralcraftapi.profile.components.PlayerResourceManager;
import org.jetbrains.annotations.NotNull;

public interface ResourceHolder {
    @NotNull PlayerResourceManager getResources();
}
