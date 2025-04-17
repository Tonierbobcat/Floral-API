package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import com.loficostudios.floralcraftapi.world.TeleportResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class TravelManager {
    private final ConcurrentHashMap<UUID, BukkitTask> animationTasks = new ConcurrentHashMap<>();

    private final String[] anim = new String[]{
            "§b✈ §f· · · · · · · · · · · · · · · · · · · ",
            "§8· §b✈ §f· · · · · · · · · · · · · · · · · · ",
            "§8· · §b✈ §f· · · · · · · · · · · · · · · · · ",
            "§8· · · §b✈ §f· · · · · · · · · · · · · · · · ",
            "§8· · · · §b✈ §f· · · · · · · · · · · · · · · ",
            "§8· · · · · §b✈ §f· · · · · · · · · · · · · · ",
            "§8· · · · · · §b✈ §f· · · · · · · · · · · · · ",
            "§8· · · · · · · §b✈ §f· · · · · · · · · · · · ",
            "§8· · · · · · · · §b✈ §f· · · · · · · · · · · ",
            "§8· · · · · · · · · §b✈ §f· · · · · · · · · · ",
            "§8· · · · · · · · · · §b✈ §f· · · · · · · · · ",
            "§8· · · · · · · · · · · §b✈ §f· · · · · · · · ",
            "§8· · · · · · · · · · · · §b✈ §f· · · · · · · ",
            "§8· · · · · · · · · · · · · §b✈ §f· · · · · · ",
            "§8· · · · · · · · · · · · · · §b✈ §f· · · · · ",
            "§8· · · · · · · · · · · · · · · §b✈ §f· · · · ",
            "§8· · · · · · · · · · · · · · · · §b✈ §f· · · ",
            "§8· · · · · · · · · · · · · · · · · §b✈ §f· · ",
            "§8· · · · · · · · · · · · · · · · · · §b✈ §f· ",
            "§8· · · · · · · · · · · · · · · · · · · §b✈"
    };

    public void travel(Player player, Location destination, long delay) {
        travel(player, destination, delay, null);
    }

    public void travel(Player player, Location destination, long delay, @Nullable Consumer<Boolean> onTeleport) {
        if (handleEvent(player, destination))
            return;
        Validate.isTrue(destination.getWorld() != null);
        AtomicBoolean isComplete = new AtomicBoolean(false);

        var isSameAsDestination = player.getWorld().getName().equals(destination.getWorld().getName());

        FloralCraftAPI.runTaskAsynchronously(() -> {
            try {
                Thread.sleep(delay * 50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                player.teleportAsync(destination)
                        .thenAccept((success) -> {
                            if (onTeleport != null)
                                onTeleport.accept(success);
                            isComplete.set(true);
                        })
                        .exceptionally(ex -> {
                            ex.printStackTrace();
                            return null;
                        });
            } catch (Exception e) {
                isComplete.set(true);
                e.printStackTrace();
            }
        });

        playAnim(player, isComplete, isSameAsDestination, false);
    }

    private boolean handleEvent(Player player, FloralWorld destination) {
        var event = new PlayerTravelEvent(player, destination);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    private boolean handleEvent(Player player, Location location) {
        var event = new PlayerTravelEvent(player, location);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    public void travel(Player player, FloralWorld destination, long delay) {
        if (handleEvent(player, destination))
            return;

        AtomicBoolean isComplete = new AtomicBoolean(false);

        var same = player.getWorld().getName().equals(destination.getBase().getName());

        FloralCraftAPI.runTaskAsynchronously(() -> {
            try {
                Thread.sleep(delay * 50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                destination.teleportAsync(player)
                        .thenAccept((success) -> {
                            isComplete.set(true);
                        })
                        .exceptionally(ex -> {
                            ex.printStackTrace();
                            return null;
                        });
            } catch (Exception e) {
                isComplete.set(true);
                e.printStackTrace();
            }
        });

        playAnim(player, isComplete, same, false);
    }

    public void travel(Player player, FloralWorld destination, long delay, @Nullable Consumer<Boolean> onTeleport) {
        if (handleEvent(player, destination))
            return;

        AtomicBoolean isComplete = new AtomicBoolean(false);

        var same = player.getWorld().getName().equals(destination.getBase().getName());

        FloralCraftAPI.runTaskAsynchronously(() -> {
            try {
                Thread.sleep(delay * 50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                destination.teleportAsync(player)
                        .thenAccept((success) -> {
                            if (onTeleport != null)
                                onTeleport.accept(success.equals(TeleportResult.SUCCESS));
                            isComplete.set(true);
                        })
                        .exceptionally(ex -> {
                            ex.printStackTrace();
                            return null;
                        });
            } catch (Exception e) {
                isComplete.set(true);
                e.printStackTrace();
            }
        });

        playAnim(player, isComplete, same, false);
    }

    private void playAnim(Player player, AtomicBoolean isComplete, boolean currentWorldSameAsDestination, boolean playSound) {
        var sbManager = FloralCraftAPI.inst().getScoreboardManager();
        var nms = FloralCraftAPI.getNMS();
        player.showTitle(Title.title(Component.text("Traveling..."), Component.text(anim[0])));

        var dummy = nms.spawnDummy(Collections.singleton(player), player.getLocation());

        nms.applyCamera(player, dummy);

        var rotationSpeed = 15f;
        var upwardsSpeed = 1.75f;

        var sb = sbManager.removeScoreboard(player);

        Map<Integer, Float> notes = new HashMap<>();
        notes.put(0, 1.0f);
        notes.put(1, 1.122462f);
        notes.put(2, 1.259921f);
        notes.put(3, 1.498307f);
        notes.put(4, 0f);
        notes.put(5, 2.0f);
        notes.put(6, 1.887749f);
        notes.put(7, 1.681793f);
        notes.put(8, 1.498307f);

        var task = animationTasks.put(player.getUniqueId(), FloralCraftAPI.runTaskTimer(new BukkitRunnable() {
            int animIndex = 0;
            int noteIndex = 0;
            long elapsed = 0;
            Location current = player.getLocation();
            Location center = player.getLocation();
            @Override
            public void run() {
                if (isComplete.get()) {
                    this.cancel();
                    nms.applyCamera(player, player.getEntityId());

                    player.showTitle(Title.title(Component.text(" "), Component.text(" ")));
                    animationTasks.remove(player.getUniqueId());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.round(1.5f * 20), 1, false, false));
                    player.playSound(player, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 0.75f,1);

                    if (currentWorldSameAsDestination && sb != null) {
                        sbManager.setScoreboard(player, sb);
                    }
                    return;
                }

                current = current.clone().add(0, 1 * upwardsSpeed, 0);
                current.setYaw(current.getYaw() + 1 * rotationSpeed);

                FloralCraftAPI.getNMS().teleport(Collections.singleton(player), current, dummy);

                if (animIndex >= anim.length)
                    animIndex = 0;
                player.sendTitlePart(TitlePart.SUBTITLE, Component.text(anim[animIndex]));

                if (elapsed % 2 == 0) {
                    spawnParticles(center);
                }

                if (playSound && elapsed % 3 == 0) {
                    if (noteIndex >= notes.size())
                        noteIndex = 0;
                    var pitch = notes.get(noteIndex);
                    player.playSound(current, Sound.BLOCK_NOTE_BLOCK_PLING, pitch <= 0 ? 0 : 0.5f, pitch);
                    noteIndex++;
                }

                animIndex++;
                elapsed++;
            }
        }, 0, 1));
        if (task != null)
            task.cancel();
    }

    private void spawnParticles(Location center) {
        var world = center.getWorld();
        var radius = 1;
        var particles = 30;
        for (int i = 0; i < particles; i++) {

            double angle = (2 * Math.PI / particles) * i;

            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            double y = center.getY();

            Location particleLocation = new Location(world, x, y, z);

            var options = new Particle.DustTransition(Color.BLUE, Color.WHITE, 1);

            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLocation, 1, 0, 0, 0, 0, options);
        }
    }

}
