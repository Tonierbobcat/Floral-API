package com.loficostudios.floralcraftapi.profile.components.attributes;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.Collection;

public enum Attribute {
    /**
     * this stat is good for all builds
     */
    VIGOR(new AttributeData("VIGOR", FloralCraftAPI.getConfig().attributes())),
    /**
     * this stat is used for techno builds
     */
    TECHNOLOGY(new AttributeData("TECHNOLOGY", FloralCraftAPI.getConfig().attributes())),
    /**
     * this stat is good for healing builds
     */
    FAITH(new AttributeData("FAITH", FloralCraftAPI.getConfig().attributes())),
    /**
     * this stat is good for strength builds
     */
    STRENGTH(new AttributeData("STRENGTH", FloralCraftAPI.getConfig().attributes())),
    /**
     * this attribute increases attack speed
     */
    DEXTERITY(new AttributeData("DEXTERITY", FloralCraftAPI.getConfig().attributes())),
    /**
     * This attribute is good for whip builds, and magic builds
     */
    SPIRIT(new AttributeData("SPIRIT", FloralCraftAPI.getConfig().attributes()));

    private final String display;
    private final String symbol;
    private final TextColor color;
    private final StatModifier[] buffs;

    Attribute(AttributeData data) {
        this.display = data.getDisplay();
        this.symbol = data.getSymbol();
        this.color = data.getColor();
        this.buffs = data.getBuffs();
    }

    public Collection<StatModifier> getBuffs() {
        return Arrays.asList(buffs);
    }

    public String display() {
        return display;
    }

    public String symbol() {
        return  symbol;
    }

    public TextColor color() {
        return color;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "display='" + display + '\'' +
                ", symbol='" + symbol + '\'' +
                ", color=" + color.asHexString() +
                '}';
    }
}
