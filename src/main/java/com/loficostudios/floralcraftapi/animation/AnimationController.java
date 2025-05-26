package com.loficostudios.floralcraftapi.animation;

import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import org.bukkit.scheduler.BukkitTask;

public interface AnimationController<T> {
    BukkitTask play(Animation<T> animation, T obj, Runnable onComplete);
    FloralScheduler getScheduler();
}
