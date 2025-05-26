package com.loficostudios.floralcraftapi.npc;

import org.bukkit.Location;

public interface NPCProvider {
    FloralNPC spawn(NPCData data, Location location);
    void remove(FloralNPC npc);
}
