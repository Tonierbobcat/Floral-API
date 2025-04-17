package com.loficostudios.floralcraftapi.profile.components.holders;

import com.loficostudios.floralcraftapi.profile.components.PlayerStatManager;
import org.jetbrains.annotations.NotNull;

public interface StatHolder {
    @NotNull PlayerStatManager getStats();

}
