package com.loficostudios.floralcraftapi.mythic;

import io.lumine.mythic.lib.listener.option.GameIndicators;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class that allows initializing MMOLib {@code GameIndicators} without have to provide a config file
 */
public abstract class FloralIndicators extends GameIndicators {
    public FloralIndicators(@NotNull String decimalFormat, @NotNull String format) {
        super(getConfig(decimalFormat, format));
    }

    public FloralIndicators(
            @NotNull String decimalFormat,
            @NotNull String format,
            @Nullable Double radialVelocity,
            @Nullable Double gravity,
            @Nullable Double initialUpwardVelocity,
            @Nullable Double entityHeightPercent,
            @Nullable Double entityWidthPercent,
            @Nullable Double yOffset,
            @Nullable Double rOffset,
            @Nullable Boolean move,
            @Nullable Long lifespan,
            @Nullable Long tickPeriod
    ) {
        super(getConfig(decimalFormat, format, radialVelocity, gravity, initialUpwardVelocity, entityHeightPercent, entityWidthPercent, yOffset, rOffset, move, lifespan, tickPeriod));
    }

    private static ConfigurationSection getConfig(@NotNull String decimalFormat, @NotNull String format) {
        return getConfig(decimalFormat, format, null, null, null, null, null, null, null, null, null, null);
    }

    private static ConfigurationSection getConfig(
            @NotNull String decimalFormat,
            @NotNull String format,
            @Nullable Double radialVelocity,
            @Nullable Double gravity,
            @Nullable Double initialUpwardVelocity,
            @Nullable Double entityHeightPercent,
            @Nullable Double entityWidthPercent,
            @Nullable Double yOffset,
            @Nullable Double rOffset,
            @Nullable Boolean move,
            @Nullable Long lifespan,
            @Nullable Long tickPeriod
    ) {
        var config = new MemoryConfiguration();

        config.set("decimal-format", decimalFormat);
        config.set("format", format);
        config.set("radial-velocity", radialVelocity);
        config.set("gravity", gravity);
        config.set("initial-upward-velocity", initialUpwardVelocity);
        config.set("entity-height-percent", entityHeightPercent);
        config.set("entity-width-percent", entityWidthPercent);
        config.set("y-offset", yOffset);
        config.set("r-offset", rOffset);
        config.set("move", move);
        config.set("lifespan", lifespan);
        config.set("tick-period", tickPeriod);

        return config;
    }
}
