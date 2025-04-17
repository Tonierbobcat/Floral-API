package com.loficostudios.floralcraftapi.npc;

import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.bukkit.entity.Player;

public record ClickInfo(FloralPlayer clicker, FloralNPC entity, NPCClickType click) {
    public ClickInfo(Player clicker, FloralNPC entity, NPCClickType click) {
        this(new FloralPlayer(clicker), entity, click);
    }
}
