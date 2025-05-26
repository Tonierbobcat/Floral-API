package com.loficostudios.floralcraftapi.modules.dash;

import org.bukkit.util.Vector;

public record DashContext(float speed, long duration, Vector direction) {
}
