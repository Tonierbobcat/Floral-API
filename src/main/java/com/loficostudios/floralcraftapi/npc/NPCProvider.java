package com.loficostudios.floralcraftapi.npc;

import com.loficostudios.floralcraftapi.world.entity.FloralEntity;
import org.bukkit.Location;

public interface NPCProvider {
    FloralEntity spawn(NPCData data, Location location);
    void remove(FloralEntity npc);
}
