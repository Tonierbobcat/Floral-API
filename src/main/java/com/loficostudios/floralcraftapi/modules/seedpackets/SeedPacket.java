package com.loficostudios.floralcraftapi.modules.seedpackets;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.utils.bukkit.items.ItemStackExtensions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SeedPacket {
    private final Material icon;
    private final int model;
    private final String name;
    private final String[] description;
    private final ItemStack[] contents;

    public SeedPacket(Material icon, int model, String name, String[] description, ItemStack[] contents) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.contents = contents;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public Collection<ItemStack> generateItems() {
        var result = new ArrayList<ItemStack>();
        var rand = ThreadLocalRandom.current();
        for (ItemStack item : contents) {
            var clone = item.clone();
            clone.setAmount(rand.nextInt(1, 3));
            result.add(clone);
        }
        return result;
    }

    public ItemStack build(int amount) {
        var item = ItemStack.of(icon, amount);
        var meta = Objects.requireNonNull(item.getItemMeta());
        meta.displayName(Component.text("§f" + name));
        var lore = new ArrayList<Component>();

        for (ItemStack i : contents) {
            lore.add(Component.text(" §7• ").append(ItemStackExtensions.getDisplayNameOrElseMaterialName(i).color(TextColor.color(255,255,255)).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
        }
        meta.lore(lore);
        meta.setCustomModelData(model);
        var pdc = meta.getPersistentDataContainer();
        pdc.set(FloralCraftAPI.getNamespaceKey("seed_packet"), PersistentDataType.STRING, name);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack[] getContents() {
        return contents;
    }
}
