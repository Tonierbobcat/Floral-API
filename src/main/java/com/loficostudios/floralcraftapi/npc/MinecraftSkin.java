package com.loficostudios.floralcraftapi.npc;

import java.util.Optional;

/**
 * Stores references for {@code Base64TextureData} & the skin signature.
 */
public class MinecraftSkin {

    private final String name;
    private final String signature;
    private final String texture;

    public MinecraftSkin(String name, String signature, String texture) {
        this.name = name;
        this.signature = signature;
        this.texture = texture;
    }

    public MinecraftSkin(String name, String texture) {
        this.name = name;
        this.signature = null;
        this.texture = texture;
    }

    public String name() {
        return name;
    }

    public Optional<String> signature() {
        return Optional.ofNullable(signature);
    }

    public String value() {
        return texture;
    }
}
