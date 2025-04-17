package com.loficostudios.floralcraftapi.profile.components;

import com.google.common.base.Strings;
import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.profile.components.base.ProfileComponent;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.utils.Debug;
import com.loficostudios.floralcraftapi.utils.FileUtils;
import com.loficostudios.floralcraftapi.utils.interfaces.Mappable;
import com.loficostudios.floralcraftapi.vault.VaultUpdatedEvent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class PlayerVault extends ProfileComponent implements Mappable<String> {
    private final int VAULT_SIZE = 100;

    private Map<Integer, ItemStack> contents = new LinkedHashMap<>(VAULT_SIZE);

    public PlayerVault(ProfileData data) {
        super(data);
        for (int i = 0; i < VAULT_SIZE; i++) {
            contents.put(i, null);
        }
    }

    public PlayerVault(ProfileData data, Map<Integer, String> itemMap) {
        super(data);
        itemMap.forEach((i, item) -> {
            if (Strings.isNullOrEmpty(item))
                contents.put(i, null);
            else
                contents.put(i, (ItemStack) FileUtils.deserializeStringToObject(item, ItemStack.class));
        });
    }

    public Map<Integer, ItemStack> getSlots() {
        return contents;
    }

    /**
     *
     * @param slot
     * @return
     */
    public @Nullable ItemStack getItem(int slot) {
        if (slot > contents.size()) {
            Debug.log("Invalid slot!");
            return null;
        }
        return contents.get(slot);
    }

    public ItemStack[] getContents() {
        return contents.values().toArray(ItemStack[]::new);
    }

    public CompletableFuture<Boolean> setSlots(@NotNull Map<Integer, ItemStack> map) {
        var holder = getParent().getHolder();
        if (holder.isOnline()) {
            var event = new VaultUpdatedEvent(holder.getPlayer(), this, map);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                var inventory =  holder.getPlayer().getInventory();
                var items = new ArrayList<>(map.values());
                for (ItemStack item : items) {
                    if (item == null)
                        continue;
                    inventory.addItem(item);
                }
                return CompletableFuture.supplyAsync(() -> false);
            }
        }
        return CompletableFuture.supplyAsync(() -> {
            var entrySet = new ArrayList<>(map.entrySet());
            for (Map.Entry<Integer, ItemStack> entry : entrySet) {
                var slot = entry.getKey();
                var item = entry.getValue();

                contents.put(slot, item);
            }
            return true;
        }).whenComplete((value, ex) -> {
            FloralCraftAPI.inst().getProfileManager().saveProfile(getParent());
        });
    }

    @Deprecated
    public void setSlot(Integer slot, @Nullable ItemStack item) {
        var holder = getParent().getHolder();
        if (holder.isOnline()) {
            var event = new VaultUpdatedEvent(holder.getPlayer(), this, Map.of(slot, item));
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                var inventory =  holder.getPlayer().getInventory();
                var items = new ArrayList<>(event.getSlots().values());
                for (ItemStack i : items) {
                    if (i == null)
                        continue;
                    inventory.addItem(i);
                }
                return;
            }
        }
        var itemInSlot = contents.get(slot);

        boolean updated = !areItemsSame(item, itemInSlot);

        if (updated) {
            contents.put(slot, item);
            FloralCraftAPI.inst().getProfileManager().saveProfile(getParent());
        }
    }

    private boolean areItemsSame(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        if (itemNullOrAir(item1) && itemNullOrAir(item2)) {
            return true;
        }
        if (item1 == null || item2 == null) {
            return false;
        }
        return true;
    }

    private boolean itemNullOrAir(@Nullable ItemStack item) {
        return item == null || item.getType().isAir();
    }

    @Override
    public Map<String, String> toMap() {

        LinkedHashMap<String, String> map = new LinkedHashMap<>(VAULT_SIZE);

        contents.forEach((i, item) -> {
            map.put("" + i, item != null
                    ? FileUtils.serializeObjectToString(item)
                    : ""
            );
        });

        return map;
    }
}

