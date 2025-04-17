package com.loficostudios.floralcraftapi.world;

public abstract class WorldEvent {
    /**
     * for conditions and stuff
     * @return false if conditions are not met
     */
    public abstract boolean trigger(FloralWorld world);

    /**
     * 0.5 means every ~ 2 ticks the event should trigger
     * @return
     */
    public abstract double getRate(FloralWorld world);

    public abstract void start(FloralWorld world);
}
