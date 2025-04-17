package com.loficostudios.floralcraftapi.vault;

import com.loficostudios.floralcraftapi.events.player.PlayerEvent;
import com.loficostudios.floralcraftapi.profile.components.PlayerVault;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class VaultEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final PlayerVault vault;

    public VaultEvent(Player player, PlayerVault vault) {
        super(player);
        this.vault = vault;
    }

    public PlayerVault getVault() {
        return vault;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
