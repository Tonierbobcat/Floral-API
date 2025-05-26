package com.loficostudios.floralcraftapi.utils.math;

import org.bukkit.util.Vector;

public enum Axis {
    PITCH(-1.0F, 0.0F, 0.0F),
    YAW(0.0F, -1.0F, 0.0F),
    ROLL(0.0F, 0.0F, -1.0F),
    UNKNOWN(0.0F, 0.0F, 0.0F);

    private final Vector vector;

    private Axis(float x, float y, float z) {
        this.vector = new Vector(x, y, z);
    }

    public Vector getVector() {
        return this.vector.clone();
    }
}
