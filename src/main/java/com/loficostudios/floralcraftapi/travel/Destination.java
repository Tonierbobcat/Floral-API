package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.world.FloralWorld;
import com.loficostudios.floralcraftapi.world.TeleportResult;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Optional;

public interface Destination {
    World getWorld();

    Optional<FloralWorld> getFloralWorld();

    double getX();

    double getY();

    double getZ();

    TeleportResult teleport(Player player);

    default Vector toVector() {
        return new Vector(getX(), getY(), getZ());
    }

    static Destination fromLocation(Location location) {
        Validate.isTrue(location != null);
        Validate.isTrue(location.getWorld() != null);

        return new Destination() {
            @Override
            public TeleportResult teleport(Player player) {
                return player.teleport(location) ? TeleportResult.SUCCESS : TeleportResult.FAIL_OTHER;
            }

            @Override
            public double getX() {
                return location.getX();
            }

            @Override
            public double getY() {
                return location.getY();
            }

            @Override
            public double getZ() {
                return location.getZ();
            }

            @Override
            public World getWorld() {
                return location.getWorld();
            }

            @Override
            public Optional<FloralWorld> getFloralWorld() {
                return Optional.empty();
            }
        };
    }

    static Destination fromWorld(FloralWorld world) {
        Validate.isTrue(world != null);
        var location = world.getSpawnLocation(); //todo change this

        return new Destination() {
            @Override
            public TeleportResult teleport(Player player) {
                return player.teleport(location) ? TeleportResult.SUCCESS : TeleportResult.FAIL_OTHER;
            }

            @Override
            public double getX() {
                return location.getX();
            }

            @Override
            public double getY() {
                return location.getY();
            }

            @Override
            public double getZ() {
                return location.getZ();
            }

            @Override
            public World getWorld() {
                return world.getBukkitWorld();
            }

            @Override
            public Optional<FloralWorld> getFloralWorld() {
                return Optional.of(world);
            }
        };
    }
}
