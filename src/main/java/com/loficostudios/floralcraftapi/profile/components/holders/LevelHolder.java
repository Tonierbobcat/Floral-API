package com.loficostudios.floralcraftapi.profile.components.holders;

import com.loficostudios.floralcraftapi.profile.components.level.PlayerLevelManager;
import org.jetbrains.annotations.NotNull;

public interface LevelHolder {
    @NotNull PlayerLevelManager getLevels();

}
