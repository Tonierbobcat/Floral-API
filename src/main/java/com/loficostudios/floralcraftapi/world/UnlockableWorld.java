package com.loficostudios.floralcraftapi.world;

import java.util.Collection;
import java.util.List;

public interface UnlockableWorld extends FloralWorld {
    default Collection<WorldRequirement> getRequirements() {
        return List.of();
    }
}
