package com.loficostudios.floralcraftapi.utils.bukkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Deprecated
public class ColorUtils {
    public static Component parse(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static String deserializeLegacyAmpersand(String text) {
        return text.replace("&", "§");
    }
}
