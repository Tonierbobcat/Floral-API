package com.loficostudios.floralcraftapi.vault;

import com.loficostudios.floralcraftapi.profile.components.PlayerVault;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class VaultUpdatedEvent extends VaultEvent implements Cancellable {

    private boolean cancelled;

    private final Map<Integer, @Nullable ItemStack> slots;

    public VaultUpdatedEvent(Player player, PlayerVault vault, Map<Integer, @Nullable ItemStack> slots) {
        super(player, vault);
        this.slots = slots;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public Map<Integer, @Nullable ItemStack> getSlots() {
        return slots;
    }
}
