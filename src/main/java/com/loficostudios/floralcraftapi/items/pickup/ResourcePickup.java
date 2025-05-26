package com.loficostudios.floralcraftapi.items.pickup;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import com.loficostudios.floralcraftapi.utils.ResourceEconomy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.IllformedLocaleException;
import java.util.Objects;

public class ResourcePickup {
    private final ItemStack item;
    private final Resource resource;

    public ResourcePickup(Resource resource, int amount) {
        var item = ItemStack.of(Material.DIAMOND, amount);
        var meta = Objects.requireNonNull(item.getItemMeta());
        meta.getPersistentDataContainer().set(FloralCraftAPI.getNamespaceKey("floral_item_pickup"), PersistentDataType.STRING, resource.name() + ":" + amount);

        item.setItemMeta(meta);
        this.item = item;
        this.resource = resource;
    }



    public ResourcePickup(Resource resource) {
        this(resource, 1);
    }

    public Item drop(Location location) {
        if (location.getWorld() == null)
            throw new IllformedLocaleException("World cannot be null!");
        return location.getWorld().dropItem(location, item);
    }

    public Item dropNaturally(Location location) {
        if (location.getWorld() == null)
            throw new IllformedLocaleException("World cannot be null!");
        return location.getWorld().dropItemNaturally(location, item);
    }

    public void add(Player player) {
        ResourceEconomy.add(FloralPlayer.get(player), resource, getAmount());
    }

    public int getAmount() {
        return item.getAmount();
    }

    public ItemStack getItem() {
        return item;
    }

    public Resource getResource() {
        return resource;
    }

    public static @Nullable ResourcePickup from(Item item) {
        if (item == null)
            return null;
        var itemstack = item.getItemStack();
        var meta = itemstack.getItemMeta();
        if (meta == null)
            return null;
        var s = meta.getPersistentDataContainer().get(FloralCraftAPI.getNamespaceKey("floral_item_pickup"), PersistentDataType.STRING);
        if (s == null)
            return null;
        String[] strings = s.split(":");
        if (strings.length < 2)
            return null;
        var name = strings[0];
        var amountString = strings[1];
        Resource resource;
        try {
            resource= Resource.valueOf(name);
        } catch (IllegalArgumentException ignore) {
            return null;
        }
        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException ignore) {
            return null;
        }
        return new ResourcePickup(resource, amount);
    }
}
