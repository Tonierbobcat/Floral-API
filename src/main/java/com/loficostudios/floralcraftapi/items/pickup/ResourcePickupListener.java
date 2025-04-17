package com.loficostudios.floralcraftapi.items.pickup;

import com.loficostudios.floralcraftapi.items.pickup.event.ResourcePickupEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ResourcePickupListener implements Listener {
    @EventHandler (ignoreCancelled = true)
    private void onPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player player))
            return;
        var item = e.getItem();
        var itemstack = item.getItemStack();
        if (!itemstack.hasItemMeta())
            return;
        var pickup = ResourcePickup.from(item);
        if (pickup == null)
            return;

        e.setCancelled(true);
        var event = new ResourcePickupEvent(player, pickup, item);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            e.setCancelled(true);
            if (item.isDead() || !item.isValid() || item.getItemStack().getAmount() < 1)
                return;
            item.remove();
            return;
        }

        item.remove();
        pickup.add(player);

        if (event.isPlaySound()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
        }
        new ResourcePickupIndicator(pickup.getResource())
                .displayIndicator(item);
    }
}
