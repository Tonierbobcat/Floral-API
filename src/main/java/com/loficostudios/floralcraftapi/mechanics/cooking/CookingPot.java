package com.loficostudios.floralcraftapi.mechanics.cooking;


import com.loficostudios.floralcraftapi.items.FloralFood;
import com.loficostudios.floralcraftapi.items.QualifiableItemHelper;
import com.loficostudios.floralcraftapi.utils.Debug;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class CookingPot {

    private Recipe[] recipes;
    public CookingPot(Recipe[] recipes) {
        this.recipes = recipes;
    }
    public CookingPot() {
        this.recipes = new Recipe[0];
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes.toArray(Recipe[]::new);
    }

    public int getAverageQuality(Collection<IngredientInstance> ingredients) {
        var quality = 0;
        var count = ingredients.size();
        for (IngredientInstance ingredient : ingredients) {
            quality += ingredient.quality();
        }
        return (int) Math.round((double) quality / count);
    }

    public Collection<IngredientInstance> getIngredients(ItemStack[] items) {
        var result = new ArrayList<IngredientInstance>();
        var helper = new QualifiableItemHelper();
        for (ItemStack item : items) {
            var instance = IngredientInstance.fromItem(item);
            if (instance != null)
                result.add(instance);
        }
        return result;
    }
    public Collection<Item> getItems(Block block) {
        var result = new ArrayList<Item>();
        var loc = block.getLocation().clone().add(0.5,0.5,0.5);
        var entities = loc.getNearbyEntities(0.5,0.5,0.5);
        for (Entity entity : entities) {
            if (entity instanceof Item item) {
                result.add(item);
            }
        }
        return result;
    }

    public CookResult cook(Collection<IngredientInstance> ingredients) {
        return cook(ingredients, false);
    }

    public CookResult cook(Collection<IngredientInstance> ingredients, boolean failure) {
        var quality = getAverageQuality(ingredients);

        var meats = 0;
        var fish = 0;
        var vegetables = 0;
        var fruits = 0;
        var flowers = 0;

        for (IngredientInstance ingredient : ingredients) {
            switch (ingredient.category()) {
                case FISH -> fish += ingredient.amount();
                case MEAT -> meats += ingredient.amount();
                case FRUIT -> fruits += ingredient.amount();
                case FLOWER -> flowers += ingredient.amount();
                case VEGETABLE -> vegetables += ingredient.amount();
            }
        }

        Debug.log("Meat: " + meats + " Fish: " + fish + " Vegetables: " + vegetables + " Fruits: " + fruits + " Flowers: " + flowers);
        Debug.log("Average Quality: " + quality);

        var recipe = getRecipe(meats, fish, vegetables, fruits, flowers);
        if (recipe == null) {
            return new CookResult(null, null, CookResult.Type.NO_RECIPE);
        }
        if (failure)
            return new CookResult(null, recipe, CookResult.Type.FAILURE);
        return new CookResult(recipe.result().build(quality), recipe, CookResult.Type.SUCCESS);
    }

    public CookResult cook(Player player, Collection<IngredientInstance> ingredients) {
        var rand = ThreadLocalRandom.current();
        var failure = rand.nextDouble() < 0.5;
        return cook(ingredients, failure);
    }

    public @Nullable Recipe getRecipeExact(int meats, int fish, int vegetables, int fruits, int flowers) {
        for (Recipe recipe : recipes) {
            if (recipe.meat() == meats && recipe.fish() == fish &&
                    recipe.vegetables() == vegetables && recipe.fruits() == fruits &&
                    recipe.flowers() == flowers) {
               return recipe;
            }
        }
        return null;
    }

    public @Nullable Recipe getRecipe(int meats, int fish, int vegetables, int fruits, int flowers) {
        Recipe closestRecipe = null;
        int smallestDifference = Integer.MAX_VALUE;

        for (Recipe recipe : recipes) {
            int totalDifference = Math.abs(recipe.meat() - meats) +
                    Math.abs(recipe.fish() - fish) +
                    Math.abs(recipe.vegetables() - vegetables) +
                    Math.abs(recipe.fruits() - fruits) +
                    Math.abs(recipe.flowers() - flowers);

            if (totalDifference < smallestDifference) {
                smallestDifference = totalDifference;
                closestRecipe = recipe;
            }
        }

        return closestRecipe;
    }

    public ItemStack[] getItemStacks(Block block) {
        var result = new ArrayList<ItemStack>();
        var items = getItems(block);
        for (Item item : items) {
            result.add(item.getItemStack());
        }
        return result.toArray(ItemStack[]::new);
    }

    public record Recipe(FloralFood result, int meat, int fish, int vegetables, int fruits, int flowers) {
    }

    public record CookResult(@Nullable ItemStack item, @Nullable Recipe recipe, Type type) {
        public enum Type {
            SUCCESS,
            NO_RECIPE,
            FAILURE
        }
    }
}
