package com.loficostudios.floralcraftapi.utils.bukkit;

import com.loficostudios.floralcraftapi.utils.Debug;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LocationUtils {
    public static boolean isBlockAboveAir(final World world, final int x, final int y, final int z) {
        return y > 255 || world.getBlockAt(x, y - 1, z).getType().equals(Material.AIR);
    }

    public static Location getSafeDestination(final Location loc) {
        final World world = loc.getWorld();
        final int worldMinY = -64;

//        final int worldLogicalY = 62;

        final int worldMaxY = 255;
        int x = loc.getBlockX();
        int y = (int) Math.round(loc.getY());
        int z = loc.getBlockZ();

//        if (isBlockOutsideWorldBorder(world, x, z)) {
//            x = getXInsideWorldBorder(world, x);
//            z = getZInsideWorldBorder(world, z);
//        }

        final int origX = x;
        final int origY = y;
        final int origZ = z;
        while (isBlockAboveAir(world, x, y, z)) {
            y -= 1;
            if (y < 0) {
                y = origY;
                break;
            }
        }
        if (isBlockUnsafe(world, x, y, z)) {
            x = Math.round(loc.getX()) == origX ? x - 1 : x + 1;
            z = Math.round(loc.getZ()) == origZ ? z - 1 : z + 1;
        }

        var RADIUS = 3;
        var VOLUME = getVector();


        int i = 0;
        while (isBlockUnsafe(world, x, y, z)) {
            i++;
            if (i >= VOLUME.length) {
                x = origX;
                y = constrainToRange(origY + RADIUS, worldMinY, worldMaxY);
                z = origZ;
                break;
            }
            x = origX + VOLUME[i].getBlockX();
            y = constrainToRange(origY + VOLUME[i].getBlockY(), worldMinY, worldMaxY);
            z = origZ + VOLUME[i].getBlockZ();
        }
        while (isBlockUnsafe(world, x, y, z)) {
            y += 1;
            if (y >= worldMaxY) {
                x += 1;
                break;
            }
        }
        while (isBlockUnsafe(world, x, y, z)) {
            y -= 1;
            if (y <= worldMinY + 1) {
                x += 1;
                // Allow spawning at the top of the world, but not above the nether roof
                y = Math.min(world.getHighestBlockYAt(x, z) + 1, worldMaxY);
                if (x - 48 > loc.getBlockX()) {
                    Debug.logError("Hole in floor");
                }
            }
        }
        return new Location(world, x + 0.5, y, z + 0.5, loc.getYaw(), loc.getPitch());
    }
    private static Vector[] getVector() {
        var RADIUS = 3;
        final List<Vector> pos = new ArrayList<>();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    pos.add(new Vector(x, y, z));
                }
            }
        }
        pos.sort(Comparator.comparingInt(a -> a.getBlockX() * a.getBlockX() + a.getBlockY() * a.getBlockY() + a.getBlockZ() * a.getBlockZ()));
        return pos.toArray(new Vector[0]);
    }
    private static boolean isBlockUnsafe(World world, int x, int y, int z) {
        var aboveAir = world.getBlockAt(x, y - 1, z).getType().isTransparent();
        var greaterThanMax = y > 255;
        return greaterThanMax || aboveAir;
    }


    public static int constrainToRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
}
