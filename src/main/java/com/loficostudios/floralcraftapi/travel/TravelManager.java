package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.animation.Animation;
import com.loficostudios.floralcraftapi.animation.AnimationController;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import com.loficostudios.floralcraftapi.world.TeleportResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TravelManager {

    private final AnimationController<Player> controller;

    public TravelManager(AnimationController<Player> controller) {
        this.controller = controller;
    }

    public void travel(Player player, Destination destination, Animation<Player> animation, @Nullable Consumer<Boolean> onTeleport) {
        if (isTeleportCancelled(player, destination))
            return;

        playAnim(player, animation, () -> {
            var result = destination.teleport(player);
            finalizeTeleport(result, onTeleport);
        });
    }

    private boolean isTeleportCancelled(Player player, Destination location) {
        return false;
//        var event = new PlayerTravelEvent(player, location);
//        Bukkit.getPluginManager().callEvent(event);
//        return event.isCancelled();
    }

    private void finalizeTeleport(TeleportResult result, Consumer<Boolean> onTeleport) {
        if (onTeleport != null)
            onTeleport.accept(result.equals(TeleportResult.SUCCESS));
    }

    private void playAnim(Player player, Animation<Player> animation, Runnable onComplete) {
        controller.play(animation, player, onComplete);
    }
}
