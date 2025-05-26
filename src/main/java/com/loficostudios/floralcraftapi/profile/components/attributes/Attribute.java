package com.loficostudios.floralcraftapi.profile.components.attributes;

import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.kyori.adventure.text.format.TextColor;

public class Attribute {
    private final String id;
    private final String display;
    private final String symbol;
    private final TextColor color;
    private final StatModifier[] buffs;

    public Attribute(String id, String display, String symbol, TextColor color, StatModifier[] buffs) {
        this.id = id;
        this.display = display;
        this.symbol = symbol;
        this.color = color;
        this.buffs = buffs;
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

    public String getName() {
        return id;
    }
}
