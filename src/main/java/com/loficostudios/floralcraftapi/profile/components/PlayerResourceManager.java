package com.loficostudios.floralcraftapi.profile.components;

import com.loficostudios.floralcraftapi.events.player.PlayerResourceChangedEvent;
import com.loficostudios.floralcraftapi.profile.components.base.ProfileComponent;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import com.loficostudios.floralcraftapi.profile.components.resources.ResourceInstance;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.utils.Debug;
import com.loficostudios.floralcraftapi.utils.interfaces.Mappable;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerResourceManager extends ProfileComponent implements Mappable<Map<String, Object>> {

    private final Map<Resource, ResourceInstance> resources = new HashMap<>();

    public PlayerResourceManager(ProfileData profile) {
        super(profile);
        for (Resource resource : Resource.values()) {
            resources.put(resource, new ResourceInstance(resource, 0));
        }
    }

    public PlayerResourceManager(ProfileData profile, Map<String, Map<String, Object>> resources) {
        super(profile);

        for (Map.Entry<String, Map<String, Object>> entry : resources.entrySet()) {
            Resource resource;
            try {
                resource = Resource.valueOf(entry.getKey());
            } catch (IllegalArgumentException e) {
                continue;
            }

            if (resource.getType() == Integer.class) {
                var v = entry.getValue().get("current");
                double current;
                if (v instanceof Integer) {
                    current = ((Integer) v).doubleValue();
                } else if (v instanceof Double) {
                    current = (Double) v;
                } else {
                    Debug.logError("Expected Integer or Double, found: " + v.getClass());
                    continue;
                }

                this.resources.put(resource, new ResourceInstance(resource, current));
            }
            else if (resource.getType() == Double.class) {
                double current = (Double) entry.getValue().get("current");
                this.resources.put(resource, new ResourceInstance(resource, current));
            }
        }
        if (resources.size() < Resource.values().length) {
            for (Resource resource : Resource.values()) {
                if (this.resources.containsKey(resource))
                    continue;
                this.resources.put(resource, new ResourceInstance(resource, 0));
            }
        }
    }

    @Override
    public Map<String, Map<String, Object>> toMap() {
        LinkedHashMap<String, Map<String, Object>> result = new LinkedHashMap<>();
        for (Map.Entry<Resource, ResourceInstance> entry : resources.entrySet()) {
            var resource = entry.getKey().name();
            LinkedHashMap<String, Object> values = new LinkedHashMap<>();

            var current = entry.getValue().getCurrent();
            values.put("current", current);

            result.put(resource, values);
        }
        return result;
    }

    public void setCurrent(Resource resource, double amount) {
        var inst = resources.get(resource);
        var last = inst.getCurrent();
        inst.setCurrent(amount);
        Optional.ofNullable(getParent().getHolder().getPlayer()).ifPresent((p) -> {
            var event = new PlayerResourceChangedEvent(p, resource, amount, last);
            Bukkit.getPluginManager().callEvent(event);
        });
    }

    public double getCurrent(String resource) {
        try {
            return getCurrent(Resource.valueOf(resource));
        } catch (Exception e) {
            Debug.logError("Could not get current resource. " + e.getMessage());
            return 0.0;
        }
    }

    public void setCurrent(String resource, double amount) {
        try {
            setCurrent(Resource.valueOf(resource), amount);
        } catch (Exception e) {
            Debug.logError("Could not set current resource. " + e.getMessage());
        }
    }

    public double getCurrent(Resource resource) {
        return resources.get(resource).getCurrent();
    }

    @SuppressWarnings("deprecation")
    public double getMax(Resource resource) {
        return resource.getMax(getParent());
    }

    public int getIntCurrent(Resource resource) {
        return (int) resources.get(resource).getCurrent();
    }


    public double getDoubleCurrent(Resource resource) {
        return resources.get(resource).getCurrent();
    }
}
