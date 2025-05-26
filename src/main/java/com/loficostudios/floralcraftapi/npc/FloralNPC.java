package com.loficostudios.floralcraftapi.npc;

import com.loficostudios.floralcraftapi.world.entity.FloralEntity;
import org.jetbrains.annotations.NotNull;

public interface FloralNPC extends FloralEntity {
    @NotNull NPCData getData();

    @Override
    default boolean isDead() {
        return false;
    }
    default void onClick(ClickInfo info) {
        getData().onClick(info);
    }

    void remove();
}
