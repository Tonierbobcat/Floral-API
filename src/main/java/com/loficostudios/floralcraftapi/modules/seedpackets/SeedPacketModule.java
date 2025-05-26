package com.loficostudios.floralcraftapi.modules.seedpackets;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SeedPacketModule implements Module, Listener {
    private final JavaPlugin plugin;

    private final Map<String, SeedPacket> registered = new HashMap<>();

    public SeedPacketModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void register(SeedPacket... packets) {
        for (SeedPacket packet : packets) {
            registered.put(packet.getName(), packet);
        }
    }

    public NamespacedKey getNsk() {
        return new NamespacedKey(plugin, "seed_packet");
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        var item = e.getItem();
        var player = e.getPlayer();
        if (item == null)
            return;
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (!item.hasItemMeta())
                return;
            var pdc = item.getItemMeta().getPersistentDataContainer();

            var value = pdc.get(getNsk(), PersistentDataType.STRING);
            if (value == null)
                return;
            var packet = registered.get(value);
            if (packet == null)
                return;
            e.setCancelled(true);
            item.setAmount(item.getAmount() - 1);
            var seeds = packet.generateItems();
            for (ItemStack seed : seeds) {
                if (!player.getInventory().isEmpty())
                    player.getInventory().addItem(seed);
                else {
                    player.getWorld().dropItem(player.getLocation(), seed);
                }
            }
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if (isSeedPacketItem(e.getItemInHand()))
            e.setCancelled(true);
    }

    public boolean isSeedPacketItem(@Nullable ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR) || !item.hasItemMeta())
            return false;
        var pdc = item.getItemMeta().getPersistentDataContainer();
        var value = pdc.get(getNsk(), PersistentDataType.STRING);
        if (value == null)
            return false;
        return registered.get(value) != null;
    }

    public static SeedPacketModule initialize(JavaPlugin plugin) {
        var module = new SeedPacketModule(plugin);
        module.onEnable();
        return module;
    }

    public Collection<SeedPacket> getPackets() {
        return Collections.unmodifiableCollection(registered.values());
    }
}
