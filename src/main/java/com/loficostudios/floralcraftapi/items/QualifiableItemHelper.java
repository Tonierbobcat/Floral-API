package com.loficostudios.floralcraftapi.items;

import com.loficostudios.floralcraftapi.mechanics.cooking.Ingredient;
import com.loficostudios.floralcraftapi.mechanics.cooking.IngredientInstance;
import com.loficostudios.floralcraftapi.utils.bukkit.MinecraftText;
import com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems.MMOItemExtensions;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class QualifiableItemHelper {

    public static final String QUALITY_STAT = "FLORAL_ITEM_QUALITY";
    public static final String PRICE_STAT = "FLORAL_ITEM_PRICE";

    private final Random random;

    public QualifiableItemHelper() {
        random = ThreadLocalRandom.current();
    }

    public ItemStack updateLore(NBTItem nbt) {
        final String[] qualityStrings = new String[] {
                "<color:#abb5c4>★<white>☆☆☆☆☆☆",
                "<green>★★<white>☆☆☆☆☆",
                "<color:#4340cf>★★★<white>☆☆☆☆",
                "<gradient:#8E2DE2:#4A00E0>★★★★</gradient><white>☆☆☆",
                "<gradient:#FFAA00:#FFE268>★★★★★</gradient><white>☆☆",
                "<gradient:#BD2F2F:#FF7575>★★★★★★</gradient><white>☆",
                "<gradient:#F4C4F3:#FC67FA>★★★★★★★</gradient>",
        };

        var bukkit = nbt.toItem();
        var meta = Objects.requireNonNull(bukkit.getItemMeta());
        List<Component> lore = new ArrayList<>(Objects.requireNonNullElse(meta.lore(), new ArrayList<>()));
        var quality = getQuality(nbt);
        var hasQuality = quality >= 1;

        if (MMOItemExtensions.isMMO(bukkit)) {
            return bukkit;
        }

        if (hasQuality) {
            try {
                lore = Collections.singletonList(MinecraftText.Adventure.parse("<white>ꞯᴜᴀʟɪᴛʏ: " + new String[] {
                        "<color:#abb5c4>★<white>☆☆☆☆☆☆",
                        "<green>★★<white>☆☆☆☆☆",
                        "<color:#4340cf>★★★<white>☆☆☆☆",
                        "<gradient:#8E2DE2:#4A00E0>★★★★</gradient><white>☆☆☆",
                        "<gradient:#FFAA00:#FFE268>★★★★★</gradient><white>☆☆",
                        "<gradient:#BD2F2F:#FF7575>★★★★★★</gradient><white>☆",
                        "<gradient:#F4C4F3:#FC67FA>★★★★★★★</gradient>",
                }[quality - 1]));
            } catch (IndexOutOfBoundsException ignore) {
            }
        }
        meta.lore(lore);
        bukkit.setItemMeta(meta);
        return bukkit;
    }

    public @NotNull IngredientInstance generate(Ingredient ingredient, Block origin, int amount) {
        return generate(null, ingredient, origin, amount);
    }

    public @NotNull IngredientInstance generate(Player player, Ingredient ingredient, Block origin, int amount) {
        Validate.isTrue(ingredient != null);

        var quality = player != null
                ? calculateQuality(ingredient, origin, player)
                : calculateQuality(ingredient, origin, 1);

        var price = calculatePrice(quality, ingredient);

        return new IngredientInstance(ingredient, ingredient.category(), quality, amount, price);
    }

    public IngredientInstance generate(Material type, int amount, int minQuality, int maxQuality) {
        var ingredient = Ingredient.fromItem(new ItemStack(type));
        Validate.isTrue(ingredient != null);

        int quality;
        if (minQuality < maxQuality) {
            quality = random.nextInt(minQuality, maxQuality);
        } else {
            quality = maxQuality;
        }

        double price = calculatePrice(quality, ingredient);

        return new IngredientInstance(ingredient, ingredient.category(), quality, amount, price);
    }

    public int getQuality(NBTItem nbt) {
        var quality = nbt.getInteger(QUALITY_STAT);
        if (quality <= 0)
            return -1;
        return quality;
    }
    public double getPrice(NBTItem nbt) {
        var price = nbt.getDouble(PRICE_STAT);
        return Math.max(0, price);
    }

    private int calculateQuality(Ingredient ingredient, @Nullable Block origin, Player player) {
        return calculateQuality(ingredient, origin, 1);
    }

    private int calculateQuality(Ingredient ingredient, @Nullable Block origin, double multiplier) {
        int[] qualities = new int[] { 1,2,3,4,5,6,7 };

        double[] probabilities;

        if (origin != null && inCorrectBiome(origin.getBiome(), ingredient)) {
            probabilities = new double[] {0.20, 0.20, 0.20, 0.15, 0.10, 0.10, 0.05};
        } else {
            probabilities = new double[] {0.50, 0.25, 0.15, 0.05, 0.03, 0.015, 0.005};
        }

        var quality = (Integer) null;
        double cumulative = 0.0;

        for (int i = 0; i < qualities.length; i++) {
            cumulative += probabilities[i];
            if (random.nextDouble() < cumulative) {
                quality = qualities[i];
                break;
            }
        }
        if (quality == null)
            quality = qualities[0];

        return Math.min(qualities[6], (int) Math.round(Math.max(1.0, quality * multiplier)));
    }

    public double calculatePrice(int quality, @NotNull Ingredient ingredient) {
        if (quality <= 0) {
            return 0;
        }
        var value = 0.0;
        value = ingredient.value();

        return quality * value;
    }

    private boolean inCorrectBiome(Biome current, Ingredient ingredient) {
        if (ingredient == null)
            return false;
        var inBiome = false;
        for (Biome biome : ingredient.biomes()) {
            if (current.equals(biome)) {
                inBiome = true;
                break;
            }
        }
        return inBiome;
    }
}
