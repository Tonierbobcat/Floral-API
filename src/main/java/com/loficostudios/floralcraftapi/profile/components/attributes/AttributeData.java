package com.loficostudios.floralcraftapi.profile.components.attributes;

import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Objects;

public class AttributeData {
    private final String display;
    private final String symbol;
    private final TextColor color;
    private final StatModifier[] buffs;

    public AttributeData(String display, String symbol, TextColor color, StatModifier[] buffs) {
        this.display = display;
        this.symbol = symbol;
        this.color = color;
        this.buffs = buffs;
    }

    public AttributeData(String id, ConfigurationSection config) {
        Debug.log("Initializing attribute: " + id);

        if (config == null || config.getConfigurationSection(id) == null) {
            Debug.log("Config is null. Loading defaults...");
            this.display = id;
            this.symbol = "#";
            this.color = TextColor.color(255,0,0);
            this.buffs = new StatModifier[0];
            return;
        }

        this.display = config.getString(id + ".display", id);
        this.symbol = config.getString(id + ".symbol", "#");

        var hex = config.getString(id + ".color", "#FF0000");
        this.color = Objects.requireNonNullElse(TextColor.fromHexString(hex), TextColor.color(255,0,0));

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

        this.buffs = modifiers.toArray(new StatModifier[0]);
        Debug.log("Initialized attribute: " + id);
    }

    public TextColor getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplay() {
        return display;
    }

    public StatModifier[] getBuffs() {
        return buffs;
    }
}
