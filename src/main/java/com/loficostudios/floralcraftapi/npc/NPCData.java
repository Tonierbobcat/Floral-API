package com.loficostudios.floralcraftapi.npc;

import com.loficostudios.floralcraftapi.utils.IdentifiableObject;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 *
 * You need to create a config in mythic mobs for the npc
 * <pre>
 * <code>
 * TEST_NPC
 *   Type: INTERACTION
 *   Display: 'Joe'
 *   Disguise: Player Joe setSkin joe setDynamicName false
 *   Skills:
 *    - npc{npc=<id>} @trigger ~onInteract
 *    - npc{npc=<id>} @trigger ~onDamaged
 *   Options:
 *     AlwaysShowName: true
 *     Despawn: persistent
 * <code>
 * <pre>
 */
public abstract class NPCData extends IdentifiableObject<String> {
    private final String name;
    private @Nullable MinecraftSkin skin;
    private List<? extends Component> customName;
    private boolean hasCustomName;

    @Deprecated
    protected NPCData(String id, String name, String display, @NotNull MinecraftSkin skin) {
        super(id);

        this.name = name;
        this.skin = skin;
        this.customName = List.of(Component.text(display));
        this.hasCustomName = true;
    }
    protected NPCData(String id, String name, List<? extends Component> display, @NotNull MinecraftSkin skin) {
        super(id);

        this.name = name;
        this.skin = skin;
        this.customName = display;
        this.hasCustomName = display != null && !display.isEmpty();
    }

    @Deprecated
    protected NPCData(String id, String name, List<? extends Component> display) {
        super(id);
        this.name = name;
        this.customName = display;
        this.skin = null;
        this.hasCustomName = display != null && !display.isEmpty();
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Optional<List<? extends Component>> getDisplayName() {
        if (!hasCustomName)
            return Optional.empty();
        return Optional.of(customName);
    }

    public void setCustomName(List<? extends Component> display) {
        hasCustomName = display != null && !display.isEmpty();
        this.customName = display;
    }

    public Optional<MinecraftSkin> getSkin() {
        return Optional.ofNullable(skin);
    }

    public void onClick(ClickInfo info) {
    }
}
