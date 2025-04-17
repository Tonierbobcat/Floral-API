package com.loficostudios.floralcraftapi.mechanics.dash;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.particles.ParticleTask;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import com.loficostudios.floralcraftapi.utils.ComplexCooldown;
import com.loficostudios.floralcraftapi.utils.Pair;
import com.loficostudios.floralcraftapi.utils.ResourceEconomy;
import com.loficostudios.floralcraftapi.utils.SimpleCooldown;
import com.loficostudios.floralcraftapi.utils.bukkit.MinecraftText;
import com.loficostudios.floralcraftapi.utils.interfaces.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.WeakHashMap;


public class DashListener implements Listener {

    private static final int STAMINA_COST = 5;
    private final long INTERVAL_MILLIS = 950;

    private final HashMap<UUID, Boolean> pressedThisFrame = new HashMap<>();
    private final WeakHashMap<UUID, Pair<Long, Long>> iframes = new WeakHashMap<>();
    private final Cooldown cooldowns = new ComplexCooldown(INTERVAL_MILLIS);
    private final Cooldown messageCooldowns = new SimpleCooldown(80 * 50);

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        var uuid = p.getUniqueId();
        boolean pressed = Objects.requireNonNullElse(pressedThisFrame.remove(uuid), false);
        if (!pressed)
            return;
        if (cooldowns.has(uuid))
            return;

        Location from = event.getFrom();
        Location to = event.getTo();

        if (to.equals(from) || to.distance(from) < 0.1) return;

        double deltaX = to.getX() - from.getX();
        double deltaZ = to.getZ() - from.getZ();

        if (Math.abs(deltaX) < 0.001 && Math.abs(deltaZ) < 0.001) {
            return;
        }

        double angle = Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        var player = new FloralPlayer(p);
        if (dash(player, getDirectionFromAngle(angle))) {
            cooldowns.set(player.getUniqueId());
            FloralCraftAPI.runTaskLater(() -> player.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    0.5f, 1.5f),INTERVAL_MILLIS / 50L);
        }
    }

    @EventHandler (ignoreCancelled = true)
    private void onFPressed(PlayerSwapHandItemsEvent e) {
        if (e.getPlayer().isSneaking())
            return;
        e.setCancelled(true);

        var player = new FloralPlayer(e.getPlayer());
        var current = player.getResources().getCurrent(Resource.STAMINA);

        var hasBlindness = player.bukkit().getActivePotionEffects().stream().anyMatch(effect -> effect.getType().equals(PotionEffectType.BLINDNESS));
        var inWater = player.bukkit().isInWater();

        if (hasBlindness)
            return;
        if (inWater)
            return;

        if (!player.bukkit().getGameMode().equals(GameMode.CREATIVE)) {
            var message = (String) null;
            if (current < STAMINA_COST) {
                message = "<red>Not enough stamina!";
            }
            if (message != null) {
                if(messageCooldowns.has(player.getUniqueId()))
                    return;
                player.bukkit().sendActionBar(MinecraftText.MiniMessage.parse(message));
                return;
            }
        }

        pressedThisFrame.put(player.getUniqueId(), true);
    }

    @EventHandler
    private void entityDamageEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        var pair = iframes.get(e.getEntity().getUniqueId());
        if (pair == null) {
            return;
        }

        long iframesDuration = pair.getValue();
        long startTime = pair.getKey(); //sys.currentMillis;
        long total = (System.currentTimeMillis() - startTime);

//        Debug.log("Time ms: " + total + " Max time: " + iframesDuration);

        if (total < iframesDuration) {
//            Debug.log("Invincible");
            e.setCancelled(true);
        }
        else {
            this.iframes.remove(e.getEntity().getUniqueId());
        }
    }

    private Vector getDirectionFromAngle(double angle) {
        if (angle < 0) angle += 360;

        // Normalize the angle to a unit vector (X, Y, Z)
        double radians = Math.toRadians(angle);
        double x = -Math.sin(radians); // - because Minecraft's positive X is to the east
        double z = Math.cos(radians);  // Z is positive towards the south

        return new Vector(x, 0, z); // You can set the Y component based on your needs (usually 0 for horizontal direction)
    }

    private boolean dash(FloralPlayer player, Vector direction) {
        var event = new PlayerDashEvent(player.bukkit(), STAMINA_COST);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
//            Debug.log("Dash is cancelled");
            return false;
        }

        var speed = 1.35;

        var move = new Vector(direction.getX(),0, direction.getZ()).multiply(speed);

        var gm = player.bukkit().getGameMode();
        if (gm.equals(GameMode.SURVIVAL) || gm.equals(GameMode.ADVENTURE)) {
            if (event.consumeStamina())
                ResourceEconomy.remove(player, Resource.STAMINA, event.getStaminaCost());
        }

        long iframesDurationMillis = Math.round(getAnimationLength(player.bukkit(), speed, direction) * 0.35); // iframes are 35% of the animation time
//        Debug.log("Iframes: " + iframesDurationMillis);
        iframes.put(player.getUniqueId(), new Pair<>(System.currentTimeMillis(), iframesDurationMillis));
        player.bukkit().setVelocity(move);

        playAnim(player.bukkit(), speed, direction);
        return true;
    }

    private void playAnim(Player player, double speed, Vector direction) {
        new DashParticles(player)
                .start(getAnimationLength(player, speed, direction) / 50L);
    }

    /**
     *
     * @param player
     * @param speed
     * @param direction
     * @return time it takes to play animation fully
     */
    private long getAnimationLength(Player player, double speed, Vector direction) {
        var target = player.getLocation().toVector().add(direction.multiply(speed));
        double distance = player.getLocation().toVector().distance(target);
        double time = distance / speed;
        return Math.round(time * 1000);
    }

    private static class DashParticles extends ParticleTask {

        private final Player player;

        private final Particle.DustTransition transition = new Particle.DustTransition(Color.fromRGB(59, 242, 99), Color.fromRGB(0,0,0), 3);

        private double angle = 0; // Start angle
        private final double radius = 1; // Radius of rotation
        private final double speed = Math.PI / 16; // Speed of rotation

        private DashParticles(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            Location center = player.getLocation();
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            double y = center.getY() + 0.25;

            center.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, new Location(center.getWorld(), x, y, z), 1, 0, 0, 0, 0, transition);

            angle += speed;
        }

        public void start(long duration) {
            super.start(0, 1, duration);
        }

        @Deprecated
        @Override
        public BukkitTask start(JavaPlugin plugin, long delay, long ticks) {
            return super.start(0, 1, 2*20);
        }
    }
}
