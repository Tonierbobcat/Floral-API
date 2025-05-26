package com.loficostudios.floralcraftapi.modules.dash;

import com.loficostudios.floralcraftapi.animation.Animation;
import org.bukkit.entity.Player;

public class DashConfig {
    private int staminaCost = 5;
    private long intervalMillis = 950;
    private String outOfStaminaMessage = "<red>Not enough stamina!";
    private float speed = 1.35f;

    private Class<? extends Animation<Player>> animation;

    public int staminaCost() {
        return staminaCost;
    }

    public DashConfig speed(float f) {
        this.speed = f;
        return this;
    }

    public float speed() {
        return this.speed;
    }

    public DashConfig intervalMillis(long intervalMillis) {
        this.intervalMillis = intervalMillis;
        return this;
    }

    public DashConfig staminaCost(int staminaCost) {
        this.staminaCost = staminaCost;
        return this;
    }

    public long intervalMillis() {
        return intervalMillis;
    }

    public DashConfig outOfStaminaMessage(String message) {
        this.outOfStaminaMessage = message;
        return this;
    }

    public String outOfStaminaMessage() {
        return outOfStaminaMessage;
    }

    public DashConfig animation(Class<? extends Animation<Player>> anim) {
        this.animation = anim;
        return this;
    }

    public Class<? extends Animation<Player>> animation() {
        return this.animation;
    }
}
