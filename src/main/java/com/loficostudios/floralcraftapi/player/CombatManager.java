package com.loficostudios.floralcraftapi.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;
import java.util.WeakHashMap;

public class CombatManager implements Listener {

    private static final long pvpTimer = 15 * 1000;
    private static final long combatTimer = 15 * 1000;

    private final WeakHashMap<UUID, Long> combat = new WeakHashMap<>();
    private final WeakHashMap<UUID, Long> pvp = new WeakHashMap<>();

    @EventHandler
    private void onPlayerDamagePlayer(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;

        if (!(e.getEntity() instanceof Player victim)) {
            if ((e.getDamager() instanceof Player)) {
                if (((Player) e.getDamager()).getGameMode().equals(GameMode.CREATIVE))
                    return;
                combat.put(e.getDamager().getUniqueId(), System.currentTimeMillis());
            }
            return;
        }

        if (e.getDamager() instanceof Player damager) {
            pvp.put(victim.getUniqueId(), System.currentTimeMillis());
            pvp.put(damager.getUniqueId(), System.currentTimeMillis());
        }
    }

    public boolean inCombat(Player player) {
        UUID uuid = player.getUniqueId();
        if (!combat.containsKey(uuid)) return false;

        if (System.currentTimeMillis() - combat.get(uuid) >= combatTimer) {
            combat.remove(uuid);
            return false;
        }
        return true;
    }

    public boolean inPvP(Player player) {
        UUID uuid = player.getUniqueId();
        if (!pvp.containsKey(uuid)) return false;

        if (System.currentTimeMillis() - pvp.get(uuid) >= pvpTimer) {
            pvp.remove(uuid);
            return false;
        }
        return true;
    }
}
