package com.loficostudios.floralcraftapi.world;

import com.loficostudios.floralcraftapi.registry.Registry;
import com.loficostudios.floralcraftapi.world.event.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WorldManager implements Registry<String, FloralWorld>, Listener {
    private final LinkedHashMap<String, FloralWorld> registeredWorlds = new LinkedHashMap<>();
    private final Random random;
    private JavaPlugin plugin;

    private final WorldProvider provider;

    public WorldProvider getProvider() {
        return provider;
    }

    public WorldManager(JavaPlugin plugin, WorldProvider provider) {
        this.random = new Random(System.currentTimeMillis());
        this.plugin = plugin;
        this.provider = provider;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FloralWorld world : registeredWorlds.values()) {
                    if (!world.isActive())
                        continue;
                    world.update();
                    if (world.getPlayers().isEmpty())
                        continue;
                    for (WorldEvent e : world.getEvents()) {
                        var rate = e.getRate(world);
                        if (random.nextDouble() > rate)
                            continue;
                        var triggered = e.trigger(world);
                        if (!triggered)
                            continue;
                        var event = new FloralWorldEventStartEvent(world, e);
                        if (event.isCancelled())
                            continue;
                        startEvent(world, e);
                        break;
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void startEvent(FloralWorld world, WorldEvent event) {
        event.start(world);
    }

    public FloralWorld getWorld(World world) {
        return getWorld(world.getName());
    }

    public FloralWorld getWorld(String name) {
        return registeredWorlds.get(name);
    }

    public FloralWorld getWorld(Class<? extends FloralWorld> clazz) {
        var worlds = registeredWorlds.values();
        for (FloralWorld world : worlds) {
            if (world.getClass().equals(clazz))
                return world;
        }
        return null;
    }

    @Override
    public @Nullable FloralWorld get(String id) {
        return registeredWorlds.get(id);
    }

    @Override
    public boolean register(FloralWorld world) {
        Validate.isTrue(!registeredWorlds.containsKey(world.getName()), "World is already registered!");

        registeredWorlds.put(world.getName(), world);
        Bukkit.getPluginManager().registerEvents(world, plugin);
        return true;
    }

    @Override
    public Collection<FloralWorld> getRegistered() {
        return List.of();
    }

    public void unregister(String name) {
        FloralWorld world = registeredWorlds.remove(name);
        if (world != null) {
            HandlerList.unregisterAll(world);
        }
    }

    public void unregister(World world) {
        unregister(world.getName());
    }

    public Collection<FloralWorld> getWorlds() {
        return Collections.unmodifiableCollection(registeredWorlds.values());
    }

    @EventHandler
    private void onWorldChanged(PlayerChangedWorldEvent e) {
        var to = getWorld(e.getPlayer().getWorld());
        var from = getWorld(e.getFrom());
        if (to != null) {
            Bukkit.getPluginManager().callEvent(new FloralWorldEnterEvent(to, from,
                    e.getPlayer(),
                    WorldEnterReason.CHANGED));
        }
        if (from != null) {
            Bukkit.getPluginManager().callEvent(new FloralWorldExitEvent(to, from,
                    e.getPlayer(),
                    WorldExitReason.CHANGED));
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        var world = getWorld(e.getPlayer().getWorld());
        if (world == null)
            return;
        Bukkit.getPluginManager().callEvent(new FloralWorldEnterEvent(world, null,
                e.getPlayer(),
                WorldEnterReason.JOIN));
    }
    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        var world = getWorld(e.getPlayer().getWorld());
        if (world == null)
            return;
        Bukkit.getPluginManager().callEvent(new FloralWorldExitEvent(world, null,
                e.getPlayer(),
                WorldExitReason.QUIT));
    }
}
