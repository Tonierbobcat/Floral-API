package com.loficostudios.floralcraftapi.utils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class FloralScheduler {
    private final JavaPlugin plugin;

    public FloralScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runTask(Runnable runnable) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTask(plugin);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTask(plugin);
        }
    }

    public BukkitTask runTaskAsynchronously(Runnable runnable) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTaskAsynchronously(plugin);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskAsynchronously(plugin);
        }
    }

    public BukkitTask runTaskLater(Runnable runnable, long delay) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTaskLater(plugin, delay);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskLater(plugin, delay);
        }
    }

    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTaskLaterAsynchronously(plugin, delay);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskLaterAsynchronously(plugin, delay);
        }
    }

    public BukkitTask runTaskTimer(Runnable runnable, long delay, long period) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTaskTimer(plugin, delay, period);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskTimer(plugin, delay, period);
        }
    }

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        if (runnable instanceof BukkitRunnable) {
            return ((BukkitRunnable) runnable).runTaskTimerAsynchronously(plugin, delay, period);
        } else {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTaskTimerAsynchronously(plugin, delay, period);
        }
    }

}
