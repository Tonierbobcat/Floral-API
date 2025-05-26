package com.loficostudios.floralcraftapi.modules.dash;

import com.loficostudios.floralcraftapi.animation.Animation;
import com.loficostudios.floralcraftapi.events.player.PlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class PlayerDashEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;
    private boolean consumeStamina = true;
    private double staminaCost;
    private float speed;
    private Vector direction;
    private Function<DashContext, Animation<Player>> animation;

    public PlayerDashEvent(Player player, int staminaCost, float speed, Vector direction, Function<DashContext, Animation<Player>> animation) {
        super(player);
        this.staminaCost = staminaCost;
        this.speed = speed;
        this.direction = direction;
        this.animation = animation;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isConsumeStamina() {
        return consumeStamina;
    }

    public void setConsumeStamina(boolean consumeStamina) {
        this.consumeStamina = consumeStamina;
    }

    public double getStaminaCost() {
        return staminaCost;
    }

    public void setStaminaCost(double staminaCost) {
        this.staminaCost = staminaCost;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Function<DashContext, Animation<Player>> getAnimation() {
        return animation;
    }

    public void setAnimation(Function<DashContext, Animation<Player>> animation) {
        this.animation = animation;
    }

    public Animation<Player> getAnimation(long duration) {
        return animation.apply(new DashContext(speed, duration, direction));
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}