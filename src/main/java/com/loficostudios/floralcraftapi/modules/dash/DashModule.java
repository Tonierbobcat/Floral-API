package com.loficostudios.floralcraftapi.modules.dash;

import com.loficostudios.floralcraftapi.modules.Module;
import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class DashModule implements Module {

    private final JavaPlugin plugin;

    private final DashConfig config = new DashConfig();

    private final DashListener listener;

    public DashModule(JavaPlugin plugin) {
        this.plugin = plugin;
        listener = new DashListener(config, new FloralScheduler(plugin));
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
    }

    public DashConfig getConfig() {
        return config;
    }

    public static DashModule initialize(JavaPlugin plugin) {
        var module = new DashModule(plugin);
        module.onEnable();
        return module;
    }
}
