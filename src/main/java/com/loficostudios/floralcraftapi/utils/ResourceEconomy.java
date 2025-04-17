package com.loficostudios.floralcraftapi.utils;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;

public class ResourceEconomy {

    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE;
    }



    private static void performOperation(FloralPlayer player, Resource resource, double amount, Operation operation) {
        var resources = player.getResources();
        if (resource.getType() == Integer.class && amount % 1 != 0) {
            throw new IllegalArgumentException(
                    "Resource " + resource + " requires integer values. Got: " + amount
            );
        }

        double current = resources.getDoubleCurrent(resource);
        double result = switch (operation) {
            case ADD -> current + amount;
            case SUBTRACT -> current - amount;
            case MULTIPLY -> current * amount;
            case DIVIDE -> {
                if (amount == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                yield current / amount;
            }
        };

        // Round result for integer resources
        if (resource.getType() == Integer.class) {
            result = Math.round(result);
        }

        resources.setCurrent(resource, result);
    }

    public static boolean has(FloralPlayer player, Resource resource, double amount) {
        var resources = player.getResources();
        double current = resources.getDoubleCurrent(resource);
        return current >= amount;
    }

    public static void remove(FloralPlayer player, Resource resource, double amount) {
        performOperation(player, resource, amount, Operation.SUBTRACT);
    }

    public static void add(FloralPlayer player, Resource resource, double amount) {
        performOperation(player, resource, amount, Operation.ADD);
    }
}
