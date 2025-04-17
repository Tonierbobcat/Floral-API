package com.loficostudios.floralcraftapi.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerExtension {

    private final Player player;

    public PlayerExtension(@NotNull Player player) {
        this.player = player;
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public void sendMessage(@NotNull Component component) {
        player.sendMessage(component);
    }

    public void setLevel(int level) {
        player.setLevel(level);
    }

    public void setExperience(int ex) {
        player.setExp(ex);
    }

    /**
     * Base should only be retrived if you need to get the org.bukkit.entity.Player
     * Just use the methods that are in player extention
     * @return
     */
    public Player bukkit() {
        return this.player;
    }

    public void playSound(@NotNull Location source, @NotNull Sound sound, SoundCategory category, float volume, float pitch) {
        player.playSound(source, sound, category, volume, pitch);
    }
    public void playSound(@NotNull Location source, @NotNull  Sound sound, float volume, float pitch) {
        playSound(source, sound, SoundCategory.MASTER, volume, pitch);
    }
    public void playSound(@NotNull Entity source, @NotNull  Sound sound, float volume, float pitch) {
        playSound(source.getLocation(), sound, volume, pitch);
    }
    public void playSound(@NotNull Sound sound, float volume, float pitch) {
        playSound(this.player, sound, volume, pitch);
    }
}
