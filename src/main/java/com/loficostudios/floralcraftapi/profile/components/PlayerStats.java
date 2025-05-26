package com.loficostudios.floralcraftapi.profile.components;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.profile.components.base.ProfileComponent;
import com.loficostudios.floralcraftapi.registry.impl.StatRegistry;
import com.loficostudios.floralcraftapi.utils.LinearValue;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierSource;
import io.lumine.mythic.lib.player.modifier.ModifierType;

import java.util.Map;

public class PlayerStats extends ProfileComponent {

    private final StatRegistry registry;

    public PlayerStats(PlayerProfile profile, StatRegistry registry) {
        super(profile);
        this.registry = registry;
    }

    public StatMap getMap() {
        return getParent().getMMO().getStatMap();
    }

    public double getBase(String stat) {
        return calculateStat(stat, getParent().getLevels().current());
    }

    public double getStat(String stat) {
        return getMap().getStat(stat);
    }

    public void update() {
        for (Map.Entry<String, LinearValue> stat : registry.getStats().entrySet()) {
            var instance = getMap().getInstance(stat.getKey());
            final StatInstance.ModifierPacket packet = instance.newPacket();
            packet.remove("floral-stat");
            final double total = getBase(instance.getStat()) - instance.getBase();
            if (total != 0) {
                packet.addModifier(new StatModifier("floral-stat", instance.getStat(), total, ModifierType.FLAT, EquipmentSlot.OTHER, ModifierSource.OTHER));
            }
            packet.runUpdate();
        }
    }

    public double calculateStat(String stat, int level) {
        var value = registry.getStats().get(stat);
        if (value == null)
            return 0;
        return value.calculate(level);
    }
}
