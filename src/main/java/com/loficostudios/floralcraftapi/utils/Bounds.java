package com.loficostudios.floralcraftapi.utils;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class Bounds {
    private final BlockLocation min;
    private final BlockLocation max;

    public Bounds(BlockLocation min, BlockLocation max) {
        int minX = Math.min(min.getX(), max.getX());
        int minY = Math.min(min.getY(), max.getY());
        int minZ = Math.min(min.getZ(), max.getZ());

        int maxX = Math.max(min.getX(), max.getX());
        int maxY = Math.max(min.getY(), max.getY());
        int maxZ = Math.max(min.getZ(), max.getZ());

        this.min = new BlockLocation(minX, minY, minZ);
        this.max = new BlockLocation(maxX, maxY, maxZ);
    }

    public BlockLocation getMin() {
        return min;
    }

    public BlockLocation getMax() {
        return max;
    }

    public boolean contains(Vector v) {
        return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX() + 1, max.getY() + 1, max.getZ() + 1).contains(v);
    }

    public boolean contains(Location location) {
        return contains(location.getX(), location.getY(), location.getZ());
    }

    public boolean contains(double x, double y, double z) {
        return contains(new Vector(x,y,z));
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
