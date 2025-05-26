package com.loficostudios.floralcraftapi.travel;

import com.loficostudios.floralcraftapi.animation.Animation;
import com.loficostudios.floralcraftapi.nms.NMSProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TravelAnimation implements Animation<Player> {
    private final NMSProvider nms;
    private boolean complete;
    private final long maxTimeTicks;

    private Location current;
    private Location center;
    private int dummy;
    private float particleTimer = 0f;

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

    public TravelAnimation(NMSProvider nms, long maxTimeTicks) {
        this.nms = nms;
        this.maxTimeTicks = maxTimeTicks;
    }


    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public void update(Player player, float elapsedTime, float delta) {
        if (elapsedTime >= maxTimeTicks / 20f) {
            complete = true;
            return;
        }

        var rotationSpeed = 15f;
        var upwardsSpeed = 1.75f;

        current = current.clone().add(0, 1 * upwardsSpeed, 0);
        current.setYaw(current.getYaw() + 1 * rotationSpeed);

        float frameDuration = 0.1f;
        int frameIndex = (int)(elapsedTime / frameDuration) % anim.length;
        player.sendTitlePart(TitlePart.SUBTITLE, Component.text(anim[frameIndex]));

        nms.teleport(Collections.singleton(player), current, dummy);

        particleTimer += delta;

        if (particleTimer >= 0.2f) {
            spawnParticles(center);
            particleTimer = 0f;
        }
    }

    @Override
    public void start(Player player) {
        player.showTitle(Title.title(Component.text("Traveling..."), Component.text("")));

        dummy = nms.spawnDummy(Collections.singleton(player), player.getLocation());

        current = player.getLocation();
        center = player.getLocation();

        nms.applyCamera(player, dummy);
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

    @Override
    public void stop(Player player) {
        nms.applyCamera(player, player.getEntityId());

        player.showTitle(Title.title(Component.text(" "), Component.text(" ")));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.round(1.5f * 20), 1, false, false));
        player.playSound(player, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 0.75f,1);
    }
}
