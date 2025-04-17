package com.loficostudios.floralcraftapi.registry.impl;

import com.loficostudios.floralcraftapi.npc.NPCData;
import com.loficostudios.floralcraftapi.registry.Registry;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Deprecated
public class NPCRegistry implements Registry<String, NPCData> {

    private final Map<String, NPCData> registered = new HashMap<>();

    public NPCRegistry() {
    }

    @Override
    public boolean register(NPCData npc) {
        registered.put(npc.getID(), npc);
        return true;
    }

    @Override
    public Collection<NPCData> getRegistered() {
        return Collections.unmodifiableCollection(registered.values());
    }

    public @Nullable NPCData get(@NotNull String id) {
        Validate.isTrue(id != null);
        var npcs = new ArrayList<>(this.registered.values());
        for (NPCData npc : npcs) {
            if (npc.getID().equals(id))
                return npc;
        }
        return null;
    }
}
