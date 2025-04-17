package com.loficostudios.floralcraftapi.registry.impl;

import com.loficostudios.floralcraftapi.registry.Registry;
import com.loficostudios.floralcraftapi.utils.Debug;
import com.loficostudios.floralcraftapi.utils.LinearValue;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatRegistry implements Registry<String, LinearValue> {

    private final Map<String, LinearValue> registeredStats = new HashMap<>();

    @SuppressWarnings("unchecked")
    public StatRegistry(ConfigurationSection config) {
        if (config == null)
            throw new IllegalArgumentException("config is null");
        for (String stat : config.getConfigurationSection("default").getKeys(false)) {
            Debug.log(stat);
            try {
                registeredStats.put(stat, new LinearValue(config.getConfigurationSection("default").getConfigurationSection(stat)));
            } catch (Exception e) {
                Debug.logError("Could not register " + stat + ". " + e.getMessage());
            }
        }
        if (!registeredStats.isEmpty())
            Debug.log("Registered " + registeredStats.size() + " stats!");
    }

    public Map<String, LinearValue> getStats() {
        return registeredStats;
    }

    @Override
    public @Nullable LinearValue get(String id) {
        return registeredStats.get(id);
    }

    @Override
    @Deprecated
    public boolean register(LinearValue object) {
        return false;
    }

    @Override
    @Deprecated
    public Collection<LinearValue> getRegistered() {
        return Collections.unmodifiableCollection(registeredStats.values());
    }
}
