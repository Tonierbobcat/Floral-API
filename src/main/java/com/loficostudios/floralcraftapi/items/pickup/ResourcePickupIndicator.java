package com.loficostudios.floralcraftapi.items.pickup;

import com.loficostudios.floralcraftapi.mythic.FloralIndicators;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import io.lumine.mythic.lib.api.event.IndicatorDisplayEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.Random;

public class ResourcePickupIndicator extends FloralIndicators {
    private final Resource resource;
    private final Random random = new Random(System.currentTimeMillis());
    public ResourcePickupIndicator(Resource resource) {
        super(
                "#.##",
                "{value}",
                0.0,
                0.0,
                new Random(System.currentTimeMillis()).nextDouble(0.05, 0.1),
                1.0,
                0.0,
                0.2,
                1.0,
                true,
                40L,
                1L
        );
        this.resource = resource;
    }
    public ResourcePickupIndicator(Resource resource, ConfigurationSection config) {
        super(
                config.getString("decimal-format", "#.##"),
                config.getString("format", "{value}"),
                config.getDouble("radial-velocity", 0.0),
                config.getDouble("gravity", 0.0),
                config.getDouble("initial-upward-velocity", 0.2),
                config.getDouble("entity-height-percent", 1.0),
                config.getDouble("entity-width-percent", 0.0),
                config.getDouble("y-offset", 0.2),
                config.getDouble("r-offset", 1),
                config.getBoolean("move", true),
                config.getLong("lifespan", 40L),
                config.getLong("tick-period", 1L)
        );
        this.resource = resource;
    }

    public void displayIndicator(Item item) {
        var color = "<color:" + resource.getColor().replace("&", "") + ">";
        displayIndicator(item,
                String.format("%s &f+%d", color + resource.getSymbol(), 1),
                new Vector(0,1,0),
                IndicatorDisplayEvent.IndicatorType.DAMAGE);
    }
}
