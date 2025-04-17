package com.loficostudios.floralcraftapi.mechanics.cooking;


import com.loficostudios.floralcraftapi.utils.Debug;
import com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems.MMOItemExtensions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Objects;

public class CookingPotListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        var block = e.getClickedBlock();
        if (block == null || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        var inHand = e.getItem();
        if (inHand == null || !inHand.getType().equals(Material.STICK) || MMOItemExtensions.isMMO(inHand))
            return;

        if (!block.getType().equals(Material.CAULDRON))
            return;
        var player = e.getPlayer();
        var below = block.getLocation().clone().add(0, -1,0).getBlock();
        if (!below.getType().equals(Material.CAMPFIRE)) {
//            MessageUtils.sendMessage(player, "Cooking pot is not above a camp fire!");
            return;
        }

        var fire = ((Campfire) below.getBlockData());
        if (!fire.isLit()) {
//            MessageUtils.sendMessage(player, "The fire must be list!");
            return;
        }

        var pot = new CookingPot();

        var event = new PrepareCookingPotEvent(pot, player, block);
        Bukkit.getPluginManager().callEvent(event);

        var itemsInPot = pot.getItemStacks(block);
        var ingredients = pot.getIngredients(itemsInPot);

        Debug.log("Items in pot: " + Arrays.toString(itemsInPot));
        Debug.log("Ingredients: " + ingredients);
        if (ingredients.isEmpty())
            return;

        var result = pot.cook(e.getPlayer(), ingredients);
        switch (result.type()) {
            case SUCCESS -> {
                if (result.item() == null)
                    return;
                removeItemsInPot(block, pot, Objects.requireNonNull(result.recipe()));
                playSuccessAnim(block, result.item(), player);
            }
            case FAILURE -> {
                playFailureAnimation(block, player);
                for (Item item : pot.getItems(block)) {
                    item.remove();
                }
            }
            case NO_RECIPE -> {
                player.playSound(player, Sound.ITEM_BOTTLE_EMPTY, 0.75f, 1);
            }
        }
    }

    public void removeItemsInPot(Block block, CookingPot pot, CookingPot.Recipe recipe) {
        var meatLeft = recipe.meat();
        var fishLeft = recipe.fish();
        var vegetablesLeft = recipe.vegetables();
        var fruitsLeft = recipe.fruits();
        var flowersLeft = recipe.flowers();

        for (Item item : pot.getItems(block)) {
            var stack = item.getItemStack();
            var ingredient = IngredientInstance.fromItem(stack);

            if (ingredient == null)
                continue;

            var category = ingredient.category();
            int amount = stack.getAmount();

            switch (category) {
                case MEAT -> {
                    if (meatLeft > 0) {
                        int toRemove = Math.min(amount, meatLeft);
                        stack.setAmount(amount - toRemove);
                        meatLeft -= toRemove;
                    }
                }
                case FISH -> {
                    if (fishLeft > 0) {
                        int toRemove = Math.min(amount, fishLeft);
                        stack.setAmount(amount - toRemove);
                        fishLeft -= toRemove;
                    }
                }
                case VEGETABLE -> {
                    if (vegetablesLeft > 0) {
                        int toRemove = Math.min(amount, vegetablesLeft);
                        stack.setAmount(amount - toRemove);
                        vegetablesLeft -= toRemove;
                    }
                }
                case FRUIT -> {
                    if (fruitsLeft > 0) {
                        int toRemove = Math.min(amount, fruitsLeft);
                        stack.setAmount(amount - toRemove);
                        fruitsLeft -= toRemove;
                    }
                }
                case FLOWER -> {
                    if (flowersLeft > 0) {
                        int toRemove = Math.min(amount, flowersLeft);
                        stack.setAmount(amount - toRemove);
                        flowersLeft -= toRemove;
                    }
                }
            }

            if (stack.getAmount() <= 0) {
                item.remove();
            }
        }
    }

    public void playSuccessAnim(Block pot, ItemStack result, Player player) {
        var world = pot.getWorld();
        var item = world.dropItem(pot.getLocation().toCenterLocation(), result);
        player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.75f, 1);
        item.setVelocity(new Vector(0, 0.6, 0));
    }

    public void playFailureAnimation(Block pot, Player player) {
        player.playSound(pot.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.75f, 1);
    }

}
