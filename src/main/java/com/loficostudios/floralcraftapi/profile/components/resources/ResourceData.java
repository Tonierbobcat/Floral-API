package com.loficostudios.floralcraftapi.profile.components.resources;

import com.loficostudios.floralcraftapi.profile.PlayerProfile;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ResourceData {
    private final String color;
    private final String symbol;
    private final String display;

    private final Function<PlayerProfile, Double> current;

    private final Function<PlayerProfile, Double> max;

    private final BiConsumer<PlayerProfile, Double> set;

    public ResourceData(String display, String color, String symbol, Function<PlayerProfile, Double> max, Function<PlayerProfile, Double> current, BiConsumer<PlayerProfile, Double> set) {
        this.color = color;
        this.symbol = symbol;
        this.display = display;
        this.current = current;
        this.max = max;
        this.set = set;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplay() {
        return display;
    }

    public Function<PlayerProfile, Double> getCurrent() {
        return current;
    }

    public Function<PlayerProfile, Double> getMax() {
        return max;
    }

    public BiConsumer<PlayerProfile, Double> getSet() {
        return set;
    }
}
