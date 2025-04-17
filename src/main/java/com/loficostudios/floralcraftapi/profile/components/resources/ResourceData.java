package com.loficostudios.floralcraftapi.profile.components.resources;

import com.loficostudios.floralcraftapi.profile.impl.ProfileData;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ResourceData {
    private final String color;
    private final String symbol;
    private final String display;

    private final Function<ProfileData, Double> current;

    private final Function<ProfileData, Double> max;

    private final BiConsumer<ProfileData, Double> set;

    public ResourceData(String display, String color, String symbol, Function<ProfileData, Double> max, Function<ProfileData, Double> current, BiConsumer<ProfileData, Double> set) {
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

    public Function<ProfileData, Double> getCurrent() {
        return current;
    }

    public Function<ProfileData, Double> getMax() {
        return max;
    }

    public BiConsumer<ProfileData, Double> getSet() {
        return set;
    }
}
