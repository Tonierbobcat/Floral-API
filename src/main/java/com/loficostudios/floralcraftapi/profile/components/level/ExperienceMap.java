package com.loficostudios.floralcraftapi.profile.components.level;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Experience to Level Map. Extremely useful for any use case.
 */
public class ExperienceMap {

    private final Map<Integer, Integer> experience = new HashMap<>();

    private final int startExperience;
    private final int maxExperience;

    public ExperienceMap(ConfigurationSection config) {
        if (config == null)
            throw new IllegalArgumentException("Config is null");
        var a = config.getKeys(false);

        if (a.isEmpty())
            throw new IllegalArgumentException("Experience list cannot be empty");
        for (String level : a) {
            var exp = config.getInt(level);
            experience.put(Integer.parseInt(level), exp);
        }

//        startLevel = ;
//        maxLevel = Collections.max(experience.keySet());

        startExperience = Collections.min(experience.values());
        maxExperience = Collections.max(experience.values());
    }

    public int get(int level) {
        return this.experience.getOrDefault(level, 0);
    }

    public int startExperience() {
        return this.startExperience;
    }

    public int maxExperience() {
        return this.maxExperience;
    }
}
