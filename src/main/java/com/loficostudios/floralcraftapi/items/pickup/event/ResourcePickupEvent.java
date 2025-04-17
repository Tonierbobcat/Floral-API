package com.loficostudios.floralcraftapi.items.pickup.event;

import com.loficostudios.floralcraftapi.items.pickup.ResourcePickup;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ResourcePickupEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final ResourcePickup pickup;

    private boolean cancelled;

    private final Item item;

    private boolean playSound = true;

    public ResourcePickupEvent(Player player, ResourcePickup pickup, Item item) {
        this.player = player;
        this.pickup = pickup;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }


    public Player getPlayer() {
        return player;
    }

    public ResourcePickup getPickup() {
        return pickup;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
