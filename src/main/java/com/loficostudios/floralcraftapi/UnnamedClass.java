package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class UnnamedClass {
    private FloralCraftAPI api;
    private FloralScheduler scheduler;

    private FloralCraftAPIConfig config;  //todo remove this
    private Function<Player, FloralPlayer> get;

    public FloralCraftAPI getApi() {
        return api;
    }

    public FloralScheduler getScheduler() {
        return scheduler;
    }

    public FloralCraftAPIConfig getConfig() {
        return config;
    }

    public FloralPlayer getPlayer(Player player) {
        if (get == null)
            throw new IllegalArgumentException();
        return get.apply(player);
    }

    public void initialize(FloralCraftAPI api, FloralCraftAPIConfig config, Function<Player, FloralPlayer> get) {
        this.api = api;
        this.config = config;
        this.scheduler = new FloralScheduler(api.getPlugin());
    }
}
