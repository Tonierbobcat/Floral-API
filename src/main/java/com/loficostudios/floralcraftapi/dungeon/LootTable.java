package com.loficostudios.floralcraftapi.dungeon;

import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LootTable {
    private final List<Entry> entries;

    public LootTable(LootTable table) {
        this.entries = table.entries;
    }

    public LootTable(List<Entry> entries) {
        this.entries = entries;
    }

    public Collection<ItemStack> generate() {
        var result = new ArrayList<ItemStack>();
        var rand = ThreadLocalRandom.current();
        for (Entry entry : entries) {
            var item = entry.item;
            var rate = entry.rate;

            if (rand.nextDouble() <= rate) {
                result.add(item.clone());
            }
        }
        return result;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public record Entry(ItemStack item, double rate) {
    }
}
