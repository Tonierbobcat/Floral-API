package com.loficostudios.floralcraftapi.mechanics.cooking;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PrepareCookingPotEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final CookingPot pot;
    private final FloralPlayer player;
    private final Block block;

    public PrepareCookingPotEvent(CookingPot pot, Player player, Block block) {
        this.pot = pot;
        this.player = new FloralPlayer(player);
        this.block = block;
    }

    public CookingPot getPot() {
        return pot;
    }

    public FloralPlayer getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
