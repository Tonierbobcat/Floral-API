package com.loficostudios.floralcraftapi.particles;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public abstract class ParticleTask extends BukkitRunnable {

    @Deprecated
    public BukkitTask start(JavaPlugin plugin, long delay, long ticks) {
        return runTaskTimer(plugin, delay, ticks);
    }

    public BukkitTask start(long delay, long ticks) {
        return FloralCraftAPI.runTaskTimer(this, delay, ticks);
    }

    public BukkitTask start(long delay, long period, long duration) {
        return FloralCraftAPI.runTaskTimer(new BukkitRunnable() {
            private long elapsedTime = 0;
            @Override
            public void run() {
                if (elapsedTime >= duration) {
                    this.cancel();
                    return;
                }
                try {
                    ParticleTask.this.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                elapsedTime++;
            }
        }, delay, period);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTask(@NotNull Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        return super.runTask(plugin);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLater(plugin, delay);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimer(plugin, delay, period);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskAsynchronously(plugin);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    @Deprecated
    @Override
    public synchronized @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }
}
