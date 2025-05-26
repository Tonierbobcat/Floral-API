package com.loficostudios.floralcraftapi.profile.components.level;

import com.loficostudios.floralcraftapi.events.player.PlayerExperienceUpdatedEvent;
import com.loficostudios.floralcraftapi.events.player.PlayerLevelUpEvent;
import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.profile.components.base.OptionalProfileComponent;
import com.loficostudios.floralcraftapi.utils.Debug;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class PlayerLevelManager extends OptionalProfileComponent {
    private int currentLevel;

    private int startLevel;
    private int maxLevel;

    private int startExperience;
    private int maxExperience;

    private double currentExperience;

    private final ExperienceMap experience;

    private final Consumer<Integer> setLevel;

    public PlayerLevelManager(@Nullable PlayerProfile data, ConfigurationSection config, @Nullable Consumer<Integer> setLevel) {
        super(data);
        this.setLevel = setLevel;
        if (config == null)
            throw new IllegalArgumentException("Config is null");
        this.experience = new ExperienceMap(config.getConfigurationSection("experience"));

        this.startLevel = (Integer) config.get("start-level");
        this.maxLevel = (Integer) config.get("max-level");

        this.startExperience = this.experience.startExperience();
        this.maxExperience = this.experience.maxExperience();

        this.currentLevel = this.startLevel;
        this.currentExperience = this.startExperience;
        Debug.log(this.toString());
    }

    public PlayerLevelManager(@Nullable PlayerProfile data, ConfigurationSection config, @Nullable Consumer<Integer> setLevel, Integer currentLevel, Integer currentExperience) {
        super(data);
        if (config == null)
            throw new IllegalArgumentException("Config is null");
        this.experience = new ExperienceMap(config.getConfigurationSection("experience"));

        this.startLevel = (Integer) config.get("start-level");
        this.maxLevel = (Integer) config.get("max-level");

        this.startExperience = this.experience.startExperience();
        this.maxExperience = this.experience.maxExperience();

        this.currentLevel = Objects.requireNonNullElse(currentLevel, startLevel);
        this.currentExperience = Objects.requireNonNullElse(currentExperience, startExperience);
        Debug.log(this.toString());
        this.setLevel = setLevel;
    }

    public void setLevel(int i) {
        var level = Math.max(startLevel, i);
        this.currentLevel = Math.min(maxLevel, level);
        currentExperience = experience.get(level);
        var profile = this.getParent();
        profile.ifPresent((p) -> p.getStats().update());
        if (setLevel != null)
            setLevel.accept(level);
    }

    public int getCurrentExperience() {
        return Double.valueOf(currentExperience).intValue();
    }

    public int getTotalExperience() {
        return experience.get(currentLevel) + Double.valueOf(currentExperience).intValue();
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    private void updateLevel() {
        if (currentLevel >= maxLevel || currentExperience < getRequiredExperience(currentLevel + 1))
            return;


//
//        var holder = profile.getHolder();
//        var isOnline = holder.isOnline();
        var pluginManager = Bukkit.getPluginManager();
//        var player = (FloralPlayer) null;
//        if (isOnline) {
//            player = new FloralPlayer(Objects.requireNonNull(holder.getPlayer()));
//        }

        var result = 0;
        while (currentExperience >= getRequiredExperience(currentLevel + result + 1)) {
            if (currentLevel + result >= maxLevel) {
                currentExperience = getRequiredExperience(maxLevel);
                return;
            }

            currentExperience -= getRequiredExperience(currentLevel + result + 1);
            result++;


            Debug.log("Level: " + (currentLevel + result) +
                    " Current Exp: " + currentExperience +
                    " Exp for next level: " + getRequiredExperience(currentLevel + result + 1));
        }

        if (result > 0) {
            currentLevel += result;
            int levels = result;
            var opt = getParent();

            opt.ifPresent((profile) -> {
                profile.getStats().update();
                var player = profile.getHolder().getPlayer();
                if (player != null)
                    pluginManager.callEvent(new PlayerLevelUpEvent(player, levels));
//                FloralCraftAPI.inst().getProfileManager().saveProfile(profile);  //todo call level update event
            });
            if (setLevel != null)
                setLevel.accept(this.currentLevel);
        }
    }

    public int getRequiredExperience(int level) {
        return experience.get(level);
    }

    public void addExperience(int v) {
        if (v <= 0 || currentLevel >= maxLevel)
            return;
        currentExperience += v;
        var opt = getParent();
        opt.ifPresent((profile) -> {
            if (profile.getHolder().isOnline()) {
                var player = Objects.requireNonNull(profile.getHolder().getPlayer());
                Bukkit.getPluginManager().callEvent(new PlayerExperienceUpdatedEvent(player));
            }
        });

        updateLevel();
        opt.ifPresent((profile) -> {
//            FloralCraftAPI.inst().getProfileManager().saveProfile(profile); //todo call level update event
        });
    }

    public int max() {
        return maxLevel;
    }

    public int current() {
        return Math.min(maxLevel, currentLevel);
    }

    @Override
    public String toString() {
        return "PlayerLevelManager{" +
                "currentLevel=" + currentLevel +
                ", startLevel=" + startLevel +
                ", maxLevel=" + maxLevel +
                ", startExperience=" + startExperience +
                ", maxExperience=" + maxExperience +
                ", currentExperience=" + currentExperience +
                '}';
    }

    public int getExperienceToNextLevel() {
        return experience.get(currentLevel + 1);
    }
}
