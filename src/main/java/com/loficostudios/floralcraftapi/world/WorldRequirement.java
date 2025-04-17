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
    private final @Nullable BiFunction<Player, TextComponent, Component> b;

    private final TextComponent message;

    public WorldRequirement(@NotNull TextComponent message, @NotNull Function<Player, Boolean> a) {
        this.message = message;
        this.a = a;
        this.b = null;
    }

    public WorldRequirement(@NotNull TextComponent message, @NotNull Function<Player, Boolean> a, @NotNull BiFunction<Player, TextComponent, Component> b) {
        this.message = message;
        this.a = a;
        this.b = b;
    }

    public Boolean meets(Player player) {
        return a.apply(player);
    }
    public TextComponent getMessage(Player player) {
        return b != null ? Component.text().append(b.apply(player, message))
                .build() : message;
    }
    public TextComponent getMessage() {
        return Component.text().append(message)
                .build();
    }
}
