package com.loficostudios.floralcraftapi.profile.components.holders;

import com.loficostudios.floralcraftapi.profile.components.attributes.PlayerAttributes;
import org.jetbrains.annotations.NotNull;

public interface AttributeHolder {
    @NotNull PlayerAttributes getAttributes();
}
