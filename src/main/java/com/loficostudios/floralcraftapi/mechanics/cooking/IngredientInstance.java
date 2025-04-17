package com.loficostudios.floralcraftapi.mechanics.cooking;

import com.loficostudios.floralcraftapi.items.QualifiableItemHelper;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.loficostudios.floralcraftapi.items.QualifiableItemHelper.PRICE_STAT;
import static com.loficostudios.floralcraftapi.items.QualifiableItemHelper.QUALITY_STAT;

public record IngredientInstance(Ingredient ingredient, Ingredient.Category category, int quality, int amount, double price) {
    public IngredientInstance(Ingredient ingredient, Ingredient.Category category, int quality, int amount) {
        this(ingredient, category, quality, amount, -1);
    }

    public @NotNull ItemStack getItem() {
        var item = ingredient.getItem(amount);
        var nbt = NBTItem.get(item);

        nbt.addTag(new ItemTag(QUALITY_STAT, this.quality));
        nbt.addTag(new ItemTag(PRICE_STAT, this.price));

        var helper = new QualifiableItemHelper();
        return helper.updateLore(nbt);
    }

    public static @Nullable IngredientInstance fromItem(ItemStack item) {
        var nbt = NBTItem.get(item);
        var quality = nbt.getInteger(QUALITY_STAT);
        if (quality <= 0)
            return null;
        var ingredient = Ingredient.fromItem(item);
        if (ingredient == null)
            return null;
        var helper = new QualifiableItemHelper();
        var price = helper.getPrice(nbt);
        return new IngredientInstance(ingredient, ingredient.category(), quality, item.getAmount(), price);
    }
}
