package com.loficostudios.floralcraftapi.mechanics.cooking;

import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Ingredient {

    PORK(Material.PORKCHOP, Category.MEAT, 4),
    BEEF(Material.BEEF, Category.MEAT, 5),
    MUTTON(Material.MUTTON, Category.MEAT, 7),
    CHICKEN(Material.CHICKEN, Category.MEAT, 7),
    RABBIT(Material.RABBIT, Category.MEAT, 5),

    COD(Material.COD, Category.FISH, 13, new Biome[]{Biome.OCEAN, Biome.RIVER}),
    SALMON(Material.SALMON, Category.FISH, 14, new Biome[]{Biome.COLD_OCEAN, Biome.RIVER}),
    TROPICAL_FISH(Material.TROPICAL_FISH, Category.FISH, 17, new Biome[]{Biome.WARM_OCEAN}),
    PUFFERFISH(Material.PUFFERFISH, Category.FISH, 23, new Biome[]{Biome.WARM_OCEAN}),

    APPLE(Material.APPLE, Category.FRUIT, 3, new Biome[]{Biome.FOREST, Biome.PLAINS}),
    CARROT(Material.CARROT, Category.VEGETABLE, 1, new Biome[]{Biome.PLAINS}),
    SWEET_BERRIES(Material.SWEET_BERRIES, Category.FRUIT, 3, new Biome[]{Biome.TAIGA}),
    MELON_SLICE(Material.MELON_SLICE, Category.FRUIT, 2, new Biome[]{Biome.JUNGLE}),
    MELON(Material.MELON, Category.FRUIT, 2 * 9, new Biome[]{Biome.JUNGLE}),
    POTATO(Material.POTATO, Category.VEGETABLE, 1, new Biome[]{Biome.PLAINS}),
    BEETROOT(Material.BEETROOT, Category.VEGETABLE, 2, new Biome[]{Biome.FLOWER_FOREST}),
    PITCHER_PLANT(Material.PITCHER_PLANT, Category.FLOWER, 10, new Biome[]{Biome.LUSH_CAVES}),
    TORCHFLOWER(Material.TORCHFLOWER, Category.FLOWER, 10, new Biome[]{Biome.MEADOW}),
    GLOW_BERRIES(Material.GLOW_BERRIES, Category.FRUIT, 4, new Biome[]{Biome.LUSH_CAVES}),
    PUMPKIN(Material.PUMPKIN, Category.VEGETABLE, 6, new Biome[]{Biome.PLAINS, Biome.FOREST}),
    WHEAT(Material.WHEAT, Category.FLOWER, 5, new Biome[]{Biome.PLAINS});

    private final @Nullable Material material;
    private final @Nullable String mmoType;
    private final @Nullable String mmoID;
    private final Category category;
    private final int value;
    private final Biome[] biomes;
    private static final Map<Material, Ingredient> LOOKUP = new HashMap<>();

    Ingredient(@Nullable Material material, Category category, int value, Biome[] biomes) {
        this.material = material;
        this.category = category;
        this.value = value;
        this.biomes = biomes;
        this.mmoType = null;
        this.mmoID = null;
    }

    Ingredient(@Nullable Material material, Category category, int value) {
        this.material = material;
        this.category = category;
        this.value = value;
        this.biomes = new Biome[0];
        this.mmoType = null;
        this.mmoID = null;
    }

    Ingredient(@NotNull String mmoType, @NotNull String mmoID, Category category, int value, Biome[] biomes) {
        this.material = null;
        this.category = category;
        this.value = value;
        this.biomes = biomes;
        this.mmoType = mmoType;
        this.mmoID = mmoID;
    }

    Ingredient(@NotNull String mmoType, @NotNull String mmoID, Category category, int value) {
        this.material = null;
        this.category = category;
        this.value = value;
        this.biomes = new Biome[0];
        this.mmoType = mmoType;
        this.mmoID = mmoID;
    }

    public ItemStack getItem(int amount) {
        amount = Math.max(1, amount);
        var mmo = MMOItems.plugin.getItem(mmoType, mmoID);
        if (mmo == null) {
            return new ItemStack(Objects.requireNonNullElse(material, Material.BARRIER), amount);
        }
        return mmo;
    }
    public ItemStack getItem() {
        return getItem(1);
    }

    public Material material() {
        return material;
    }

    public Biome[] biomes() {
        return biomes;
    }

    public Category category() {
        return category;
    }

    public int value() {
        return value;
    }

    public static Ingredient[] getIngredients(Category category) {
        var result = new ArrayList<Ingredient>();
        for (Ingredient value : values()) {
            if (value.category.equals(category))
                result.add(value);
        }
        return result.toArray(Ingredient[]::new);
    }

    public static @Nullable Ingredient fromItem(@NotNull ItemStack item) {
        Debug.log("Ingredient#fromItem");
        Debug.log("ItemType: " + item.getType());
        var ingredient = LOOKUP.get(item.getType());
        Debug.log("Ingredient: " + ingredient);
        return ingredient;
    }
    public static @Nullable Ingredient fromItem(NBTItem item) {
        return LOOKUP.get(item.getItem().getType());
    }

    static {
        for (Ingredient ingredient : values()) {
            if (ingredient.material == null)
                continue;
            LOOKUP.put(ingredient.material, ingredient);
        }
    }

    public enum Category {
        MEAT,
        FISH,
        FRUIT,
        VEGETABLE,
        FLOWER,
    }
}