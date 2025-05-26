package com.loficostudios.floralcraftapi.world;

import java.util.Collection;

public interface Unlockable extends FloralWorld {
    Collection<WorldRequirement> getRequirements();
}
