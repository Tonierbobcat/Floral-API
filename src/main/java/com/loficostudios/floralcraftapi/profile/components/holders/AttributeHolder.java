package com.loficostudios.floralcraftapi.profile.components.holders;

import com.loficostudios.floralcraftapi.profile.components.attributes.PlayerAttributeManager;
import org.jetbrains.annotations.NotNull;

public interface AttributeHolder {
    @NotNull PlayerAttributeManager getAttributes();
}
