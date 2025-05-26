package com.loficostudios.floralcraftapi.profile.components.attributes;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.config.Config;
import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.Objects;

public class Attributes {

    private static final Attribute[] values = {
            create("VIGOR", FloralCraftAPI.getConfig().attributes()),
            create("TECHNOLOGY", FloralCraftAPI.getConfig().attributes()),
            create("FAITH", FloralCraftAPI.getConfig().attributes()),
            create("STRENGTH", FloralCraftAPI.getConfig().attributes()),
            create("DEXTERITY", FloralCraftAPI.getConfig().attributes()),
            create("SPIRIT", FloralCraftAPI.getConfig().attributes())
    };

    /**
     * this stat is good for all builds
     */
    public static final Attribute VIGOR = values[0];

    /**
     * this stat is used for techno builds
     */
    public static final Attribute TECHNOLOGY = values[1];

    /**
     * this stat is good for healing builds
     */
    public static final Attribute FAITH = values[2];

    /**
     * this stat is good for strength builds
     */
    public static final Attribute STRENGTH = values[3];

    /**
     * this attribute increases attack speed
     */
    public static final Attribute DEXTERITY = values[4];

    /**
     * This attribute is good for whip builds, and magic builds
     */
    public static final Attribute SPIRIT = values[5];

    public static Attribute[] values() {
        return values;
    }

    private static Attribute create(String id, Config config) {
        Debug.log("Initializing attribute: " + id);
        String display;
        String symbol;
        TextColor color;
        StatModifier[] buffs;
        if (config == null || config.getConfigurationSection(id) == null) {
            Debug.log("Config is null. Loading defaults...");
            display = id;
            symbol = "#";
            color = TextColor.color(255,0,0);
            buffs = new StatModifier[0];
            return new Attribute(id, display, symbol, color, buffs);
        }

        display = config.getString(id + ".display", id);
        symbol = config.getString(id + ".symbol", "#");

        var hex = config.getString(id + ".color", "#FF0000");
        color = Objects.requireNonNullElse(TextColor.fromHexString(hex), TextColor.color(255,0,0));

        var modifiers = new ArrayList<StatModifier>();
        var sect = config.getConfigurationSection(id + ".buffs");
        if (sect != null) {
            for (String buff : sect.getKeys(false)) {
                Debug.log("found: " + buff);

                var amount = sect.getString(buff);
                Debug.log("amount: " + amount);
                if (amount == null || amount.isEmpty())
                    continue;
                modifiers.add(new StatModifier("attribute." + id.toLowerCase(), buff.toUpperCase(), amount));
            }
        }
        else {
            Debug.logWarning("Attribute: " + id + " has no buffs");
        }

        buffs = modifiers.toArray(new StatModifier[0]);
        Debug.log("Initialized attribute: " + id);
        return new Attribute(id, display, symbol, color, buffs);
    }

    public static Attribute valueOf(String name) {
        if (name == null)
            throw new NullPointerException();
        for (Attribute value : values) {
            if (value.getName().equals(name))
                return value;
        }
        throw new IllegalArgumentException();
    }
}
