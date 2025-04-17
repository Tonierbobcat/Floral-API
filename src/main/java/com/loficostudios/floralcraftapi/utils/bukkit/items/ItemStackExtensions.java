package com.loficostudios.floralcraftapi.utils.bukkit.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackExtensions {
    public static Component getDisplayNameOrElseMaterialName(@NotNull ItemStack item) {
        var meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            return meta.displayName();
        } else {
            return Component.text(getFormattedMaterialName(item.getType()));
        }
    }
    public static String getLegacyDisplayNameOrElseMaterialName(@NotNull ItemStack item) {
        var meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            return meta.getDisplayName();
        } else {
            return getFormattedMaterialName(item.getType());
        }
    }

    public static String getFormattedMaterialName(@NotNull Material type) {
        var builder = new StringBuilder();
        var words = type.name().toLowerCase().split("_");
        for (String word : words) {
            var chars = word.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            builder.append(String.valueOf(chars)).append(" ");
        }
        return builder.toString().trim();
    }
}
