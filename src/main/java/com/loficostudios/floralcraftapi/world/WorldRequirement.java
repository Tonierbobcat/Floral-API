package com.loficostudios.floralcraftapi.world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public class WorldRequirement {

    private final Function<Player, Boolean> a;
    private final Function<Player, TextComponent> b;

    public WorldRequirement(@NotNull Function<Player, TextComponent> message, @NotNull Function<Player, Boolean> a) {
        this.b = message;
        this.a = a;
    }

    public Boolean meets(Player player) {
        return a.apply(player);
    }
    public TextComponent getMessage(Player player) {
        return b.apply(player);
    }
}
