package com.loficostudios.floralcraftapi.profile.components.attributes;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.api.InstanceModifier;
import org.jetbrains.annotations.NotNull;

public class AttributeModifier extends InstanceModifier {
    private final Attribute attribute;

    public AttributeModifier(Attribute attribute, @NotNull String key, double value) {
        super(key, value);
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public void register(@NotNull MMOPlayerData mmoPlayerData) {

    }

    @Override
    public void unregister(@NotNull MMOPlayerData mmoPlayerData) {

    }
}
