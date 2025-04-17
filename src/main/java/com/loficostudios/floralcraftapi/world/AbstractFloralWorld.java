package com.loficostudios.floralcraftapi.world;

import org.bukkit.World;

public abstract class AbstractFloralWorld implements FloralWorld {
    private final World base;
    private final Settings settings;
    protected AbstractFloralWorld(World base) {
        this.base = base;
        this.settings = new Settings(base);
    }

    @Override
    public World getBase() {
        return base;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Deprecated
    @Override
    public boolean isActive() {
        return true;
    }
}
