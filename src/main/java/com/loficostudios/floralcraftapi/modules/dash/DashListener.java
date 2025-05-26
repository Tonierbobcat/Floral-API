package com.loficostudios.floralcraftapi.modules.dash;

import com.loficostudios.floralcraftapi.animation.*;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import com.loficostudios.floralcraftapi.utils.ComplexCooldown;
import com.loficostudios.floralcraftapi.utils.Pair;
import com.loficostudios.floralcraftapi.utils.ResourceEconomy;
import com.loficostudios.floralcraftapi.utils.SimpleCooldown;
import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import com.loficostudios.floralcraftapi.utils.bukkit.MinecraftText;
import com.loficostudios.floralcraftapi.utils.interfaces.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class DashListener implements Listener {

    private final HashMap<UUID, Boolean> pressedThisFrame = new HashMap<>();
    private final HashMap<UUID, Pair<Long, Long>> iframes = new HashMap<>();

    private final Cooldown cooldowns;
    private final Cooldown messageCooldowns = new SimpleCooldown(80 * 50);

    private final DashConfig config;

    private final FloralScheduler scheduler;

    private final AnimationController<Player> controller;

    private final HashMap<Class<? extends Animation<Player>>, Field> cachedAnimations = new HashMap<>();

    public DashListener(DashConfig config, FloralScheduler scheduler) {
        this.config = config;
        this.controller = new PlayerAnimationController(scheduler);
        this.cooldowns = new ComplexCooldown(config.intervalMillis());
        this.scheduler = scheduler;
    }

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
        var player = FloralPlayer.get(p);
        if (dash(player, getDirectionFromAngle(angle))) {
            cooldowns.set(player.getUniqueId());
            scheduler.runTaskLater(() -> player.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    0.5f, 1.5f),config.intervalMillis() / 50L);
        }
    }

    @EventHandler (ignoreCancelled = true)
    private void onFPressed(PlayerSwapHandItemsEvent e) {
        if (e.getPlayer().isSneaking())
            return;
        e.setCancelled(true);

        var player = FloralPlayer.get(e.getPlayer());
        var current = player.getResources().getCurrent(Resource.STAMINA);

        var hasBlindness = player.bukkit().getActivePotionEffects().stream().anyMatch(effect -> effect.getType().equals(PotionEffectType.BLINDNESS));
        var inWater = player.bukkit().isInWater();

        if (hasBlindness)
            return;
        if (inWater)
            return;

        if (!player.bukkit().getGameMode().equals(GameMode.CREATIVE)) {
            var message = (String) null;
            if (current < config.staminaCost()) {
                message = config.outOfStaminaMessage();
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

        if (total < iframesDuration) {
            e.setCancelled(true);
        }
        else {
            this.iframes.remove(e.getEntity().getUniqueId());
        }
    }

    private Vector getDirectionFromAngle(double angle) {
        if (angle < 0) angle += 360;

        double radians = Math.toRadians(angle);
        double x = -Math.sin(radians);
        double z = Math.cos(radians);

        return new Vector(x, 0, z);
    }

    private boolean dash(FloralPlayer player, Vector direction) {
        var event = new PlayerDashEvent(player.bukkit(), config.staminaCost(), config.speed(), direction, (ctx) -> Animation.create(config.animation(), ctx));
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        float speed = event.getSpeed();
        double staminaCost = event.getStaminaCost();;
        boolean consumeStamina = event.isConsumeStamina();
        long duration = getApproximateDashDurationMillis(player.bukkit(), speed, direction);

        var animation = event.getAnimation(duration);

        var move = new Vector(direction.getX(),0, direction.getZ()).multiply(speed);

        var gm = player.bukkit().getGameMode();
        if (gm.equals(GameMode.SURVIVAL) || gm.equals(GameMode.ADVENTURE)) {
            if (consumeStamina)
                ResourceEconomy.remove(player, Resource.STAMINA, staminaCost);
        }

        long iframesDurationMillis = Math.round(duration * 0.35);

        iframes.put(player.getUniqueId(), new Pair<>(System.currentTimeMillis(), iframesDurationMillis));

        player.bukkit().setVelocity(move);
        controller.play(animation, player.bukkit(), null);

        return true;
    }

    /**
     *
     * @return time it takes to play animation fully
     */
    private long getApproximateDashDurationMillis(Player player, double speed, Vector direction) {
        var target = player.getLocation().toVector().add(direction.multiply(speed));
        double distance = player.getLocation().toVector().distance(target);
        double time = distance / speed;
        return Math.round(time * 1000);
    }
}
