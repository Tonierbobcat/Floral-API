package com.loficostudios.floralcraftapi.profile.components.resources;

import com.loficostudios.floralcraftapi.profile.PlayerProfile;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum Resource {

    /**
     *
     */
    STAMINA(new ResourceData("Stamina", "&a", "꩜",
            (data) -> data.getStats().getStat("MAX_STAMINA"),
            (data) -> data.getResources().getCurrent("STAMINA"),
            (data, amount) -> {
                data.getResources().setCurrent("STAMINA", amount);
            }), Double.class),
    /**
     *
     */
    MANA(new ResourceData("Mana", "&9", "⭐",
            (data) -> data.getStats().getStat("MAX_MANA"),
            (data) -> data.getResources().getCurrent("MANA"),
            (data, amount) -> {
                data.getResources().setCurrent("MANA", amount);
            }), Double.class),
    //region UPGRADE MATERIALS
    /**
     * Arcane shards are used to upgrade the {magic} class
     */
    ARCANE_SHARD(new ResourceData("Arcane Shard", "&#9e56ff", "\uD83C\uDF16",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("ARCANE_SHARD"),
            (data, amount) -> {
                data.getResources().setCurrent("ARCANE_SHARD", amount);
            }), Integer.class),
    /**
     * Arcane shards are used to upgrade the {magic} class
     */
    GAIA_CRYSTAL(new ResourceData("Gaia Crystal", "&#67ca6b", "☀",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("GAIA_CRYSTAL"),
            (data, amount) -> {
                data.getResources().setCurrent("GAIA_CRYSTAL", amount);
            }), Integer.class),
    /**
     * Arcane shards are used to upgrade the {magic} class
     */
    ELECTRUM_BATTERY(new ResourceData("Electrum Battery", "&#5a20ff", "⚡",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("ELECTRUM_BATTERY"),
            (data, amount) -> {
                data.getResources().setCurrent("ELECTRUM_BATTERY", amount);
            }), Integer.class),
    /**
     * Arcane shards are used to upgrade the {magic} class
     */
    TITANITE_INGOT(new ResourceData("Titanite Ingot", "&#a588d4", "☄",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("TITANITE_INGOT"),
            (data, amount) -> {
                data.getResources().setCurrent("TITANITE_INGOT", amount);
            }), Integer.class),
    /**
     *
     */
    COMMON_ESSENCE(new ResourceData("Common Essence", "&#a588d4", "☄",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("COMMON_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("COMMON_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    UNCOMMON_ESSENCE(new ResourceData("Uncommon Essence", "&#4caf50", "✨",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("UNCOMMON_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("UNCOMMON_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    RARE_ESSENCE(new ResourceData("Rare Essence", "&#2196f3", "💎",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("RARE_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("RARE_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    EPIC_ESSENCE(new ResourceData("Epic Essence", "&#9c27b0", "🔥",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("EPIC_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("EPIC_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    LEGENDARY_ESSENCE(new ResourceData("Legendary Essence", "&#ff9800", "⚡",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("LEGENDARY_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("LEGENDARY_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    MYTHICAL_ESSENCE(new ResourceData("Mythical Essence", "&#e91e63", "🌟",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("MYTHICAL_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("MYTHICAL_ESSENCE", amount)), Integer.class),
    /**
     *
     */
    STARLIGHT_ESSENCE(new ResourceData("Starlight Essence", "&#ffffff", "🌌",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("STARLIGHT_ESSENCE"),
            (data, amount) -> data.getResources().setCurrent("STARLIGHT_ESSENCE", amount)), Integer.class),
    //endregion
    /**
     * Premium Currency used for cosmetics and non pay to win features such as private island
     */
    LUMA_GEMS(new ResourceData("Luma Gems", "&5", "☯",
            (data) -> Double.MAX_VALUE,
            (data) -> data.getResources().getCurrent("LUMA_GEMS"),
            (data, amount) -> {
                data.getResources().setCurrent("LUMA_GEMS", amount);
            }), Integer.class);

    private final String color;
    private final String symbol;

    private final Class<? extends Number> type;
    private final String display;

    private final Function<PlayerProfile, Double> current;

    private final Function<PlayerProfile, Double> max;

    private final BiConsumer<PlayerProfile, Double> set;

    Resource(ResourceData data, Class<? extends Number> type) {
        this.color = data.getColor();
        this.symbol = data.getSymbol();
        this.type = type;
        this.display = data.getDisplay();

        this.current = data.getCurrent();
        this.max = data.getMax();
        this.set = data.getSet();
    }

    public String getColor() {
        return color;
    }

    public String getDisplay() {
        return display;
    }

    public String getSymbol() {
        return symbol;
    }

    @Deprecated
    public double getCurrent(PlayerProfile player) {
        return current.apply(player);
    }

    @Deprecated
    public double getMax(PlayerProfile player) {
        return max.apply(player);
    }

    @Deprecated
    public void setCurrent(PlayerProfile player, double amount) {
        set.accept(player, amount);
    }

    public Class<? extends Number> getType() {
        return type;
    }
}
