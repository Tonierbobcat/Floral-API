package com.loficostudios.floralcraftapi.character;

public enum CharacterClass {
    SUPPORT("§a➕"),
    DEFENSE("§7⛨"),
    OFFENSE("§c\uD83D\uDDE1");

    private final String icon;

    CharacterClass(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
