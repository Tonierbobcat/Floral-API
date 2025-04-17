package com.loficostudios.floralcraftapi.profile.components.attributes;

import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import io.lumine.mythic.lib.player.modifier.ModifierType;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap {
    private Map<Attribute, AttributeInstance> attributes = new HashMap<>();
    private final ProfileData profile;
    public AttributeMap(ProfileData profile) {
        this.profile = profile;
        for (Attribute attribute : Attribute.values()) {
            attributes.put(attribute, new AttributeInstance(profile, attribute));
        }
    }

    public AttributeInstance getAttribute(Attribute attribute) {
        return attributes.get(attribute);
    }

    public static class AttributeInstance {
        private int base;
        private int max;

        private final Attribute attribute;
        private final ProfileData profile;

        private Map<String, AttributeModifier> modifiers = new HashMap<>();


        public AttributeInstance(ProfileData profile, Attribute attribute) {
            this.attribute = attribute;
            this.profile = profile;
        }

        public AttributeModifier getModifier(String key) {
            return modifiers.get(key);
        }

        public AttributeModifier addModifier(AttributeModifier modifier) {
            var mod = modifiers.put(modifier.getKey(), modifier);
            update();
            return mod;
        }

        public void update() {
//            Debug.log("Updating");
            var mmo = profile.getMMO();
            var total = getEffective();
            var buffs = attribute.getBuffs();
            for (StatInstance ins : mmo.getStatMap().getInstances())
                ins.removeIf(str -> str.equals("attribute." + attribute.name().toLowerCase()));
            for (StatModifier buff : buffs)
                buff.multiply(total).register(mmo);
        }

        public AttributeModifier removeModifier(AttributeModifier modifier) {
            var mod = modifiers.remove(modifier.getKey());
            update();
            return mod;
        }

        public Integer getMax() {
            return max;
        }

        public int getBase() {
            return base;
        }


        public int getEffective() {
            double x = base;

            for (AttributeModifier modifier : modifiers.values()) {
                if (modifier.getType().equals(ModifierType.FLAT))
                    x += modifier.getValue();
                else if (modifier.getType().equals(ModifierType.RELATIVE))
                    x *= modifier.getValue();
            }

            return (int) x;
        }

        public void setBase(int v) {
            setBase(v, true);
        }
        public void setBase(int v, boolean update) {
            Debug.log("setting base");
            base = Math.max(0, v);
            if (update)
                update();
        }
    }
}
