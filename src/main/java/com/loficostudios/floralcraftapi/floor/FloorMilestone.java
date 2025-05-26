package com.loficostudios.floralcraftapi.floor;

import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FloorMilestone {
    private final String title;
    private final BiFunction<Player, Floor, Boolean> function;

    public FloorMilestone(String title, BiFunction<Player, Floor, Boolean> function) {
        this.title = title;
        this.function = function;
    }

    public boolean isComplete(Player player, Floor floor) {
        return function.apply(player, floor);
    }

    public String getTitle() {
        return title;
    }
}
