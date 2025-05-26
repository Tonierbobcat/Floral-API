package com.loficostudios.floralcraftapi.animation;

import com.loficostudios.floralcraftapi.utils.bukkit.FloralScheduler;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class PlayerAnimationController implements AnimationController<Player> {
    private final FloralScheduler scheduler;
    private final HashMap<UUID, BukkitTask> animations = new HashMap<>();

    public PlayerAnimationController(FloralScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public BukkitTask play(Animation<Player> animation, Player player, Runnable onComplete) {
        animation.start(player);

        final long[] lastTime = {System.currentTimeMillis()};
        final float[] elapsedTime = {0};

        var animTask = scheduler.runTaskTimer(() -> {
            long currentTime = System.currentTimeMillis();
            float delta = (currentTime - lastTime[0]) / 1000f;
            lastTime[0] = currentTime;

            if (animation.isComplete()) {
                var task = animations.remove(player.getUniqueId());
                if (task != null)
                    task.cancel();

                animation.stop(player);
                if (onComplete != null)
                    onComplete.run();
                return;
            }

            animation.update(player, elapsedTime[0], delta);
            elapsedTime[0] += delta;
        }, 0, 1);

        var last = animations.put(player.getUniqueId(), animTask);
        if (last != null)
            last.cancel();
        return animTask;
    }

    @Override
    public FloralScheduler getScheduler() {
        return null;
    }
}
