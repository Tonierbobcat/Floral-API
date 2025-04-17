package com.loficostudios.floralcraftapi.mechanics.dash;

import com.loficostudios.floralcraftapi.events.player.PlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class PlayerDashEvent extends PlayerEvent implements Cancellable {

    private boolean cancelled;

    private boolean consumeStamina = true;
//    private boolean consumeFood = true;

    private double staminaCost;

    public boolean isConsumeStamina() {
        return consumeStamina;
    }

    public PlayerDashEvent(Player player, int staminaCost) {
        super(player);
        this.staminaCost = staminaCost;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public boolean consumeStamina() {
        return consumeStamina;
    }

//    public boolean consumeFood() {
//        return consumeFood;
//    }

//    public void setConsumeFood(boolean consumeFood) {
//        this.consumeFood = consumeFood;
//    }

    public double getStaminaCost() {
        return staminaCost;
    }

    public void setStaminaCost(double staminaCost) {
        this.staminaCost = staminaCost;
    }

    public void setConsumeStamina(boolean consumeStamina) {
        this.consumeStamina = consumeStamina;
    }
}
