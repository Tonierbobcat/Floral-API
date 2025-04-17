package com.loficostudios.floralcraftapi.config;

import com.loficostudios.floralcraftapi.file.impl.YamlFile;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config implements ConfigurationSection {
    private final @Nullable YamlFile file;
    private final ConfigurationSection config;
    public Config(YamlFile file) {
        this.file = file;
        this.config = file.getConfig();;
    }
    public Config(MemoryConfiguration config) {
        this.config = config;
        this.file = null;
    }

    public void save() {
        if (file == null)
            return;
        file.save();
    }

    @Override
    public @Nullable Vector getVector(@NotNull String s) {
        return config.getVector(s);
    }
    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@NotNull String s) {
        return config.getOfflinePlayer(s);
    }
    @Override
    public @Nullable ItemStack getItemStack(@NotNull String s) {
        return config.getItemStack(s);
    }
    @Override
    public @Nullable Color getColor(@NotNull String s) {
        return config.getColor(s);
    }
    @Override
    public @Nullable Location getLocation(@NotNull String s) {
        return config.getLocation(s);
    }

    @Override
    public @NotNull Set<String> getKeys(boolean b) {
        return config.getKeys(b);
    }

    @Override
    public @NotNull Map<String, Object> getValues(boolean b) {
        return config.getValues(b);
    }

    @Override
    public boolean contains(@NotNull String s) {
        return config.contains(s);
    }

    @Override
    public boolean contains(@NotNull String s, boolean b) {
        return config.contains(s, b);
    }

    @Override
    public boolean isSet(@NotNull String s) {
        return config.isSet(s);
    }

    @Override
    public @Nullable String getCurrentPath() {
        return config.getCurrentPath();
    }

    @Override
    public @NotNull String getName() {
        return config.getName();
    }

    @Override
    public @Nullable Configuration getRoot() {
        return config.getRoot();
    }

    @Override
    public @Nullable ConfigurationSection getParent() {
        return config.getParent();
    }

    @Override
    public @Nullable Object get(@NotNull String s) {
        return config.get(s);
    }

    @Override
    public @Nullable Object get(@NotNull String s, @Nullable Object o) {
        return config.get(s, o);
    }

    @Override
    public void set(@NotNull String s, @Nullable Object o) {
        config.set(s, o);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull String s) {
        return config.createSection(s);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull String s, @NotNull Map<?, ?> map) {
        return config.createSection(s, map);
    }

    @Override
    public @Nullable String getString(@NotNull String s) {
        return config.getString(s);
    }

    @Override
    public @Nullable String getString(@NotNull String s, @Nullable String s1) {
        return config.getString(s, s1);
    }

    @Override
    public boolean isString(@NotNull String s) {
        return config.isString(s);
    }

    @Override
    public int getInt(@NotNull String s) {
        return config.getInt(s);
    }

    @Override
    public int getInt(@NotNull String s, int i) {
        return config.getInt(s, i);
    }

    @Override
    public boolean isInt(@NotNull String s) {
        return config.isInt(s);
    }

    @Override
    public boolean getBoolean(@NotNull String s) {
        return config.getBoolean(s);
    }

    @Override
    public boolean getBoolean(@NotNull String s, boolean b) {
        return config.getBoolean(s, b);
    }

    @Override
    public boolean isBoolean(@NotNull String s) {
        return config.isBoolean(s);
    }

    @Override
    public double getDouble(@NotNull String s) {
        return config.getDouble(s);
    }

    @Override
    public double getDouble(@NotNull String s, double v) {
        return config.getDouble(s, v);
    }

    @Override
    public boolean isDouble(@NotNull String s) {
        return config.isDouble(s);
    }

    @Override
    public long getLong(@NotNull String s) {
        return config.getLong(s);
    }

    @Override
    public long getLong(@NotNull String s, long l) {
        return config.getLong(s, l);
    }

    @Override
    public boolean isLong(@NotNull String s) {
        return config.isLong(s);
    }

    @Override
    public @Nullable List<?> getList(@NotNull String s) {
        return config.getList(s);
    }

    @Override
    public @Nullable List<?> getList(@NotNull String s, @Nullable List<?> list) {
        return config.getList(s, list);
    }

    @Override
    public boolean isList(@NotNull String s) {
        return config.isList(s);
    }

    @Override
    public @NotNull List<String> getStringList(@NotNull String s) {
        return config.getStringList(s);
    }

    @Override
    public @NotNull List<Integer> getIntegerList(@NotNull String s) {
        return config.getIntegerList(s);
    }

    @Override
    public @NotNull List<Boolean> getBooleanList(@NotNull String s) {
        return config.getBooleanList(s);
    }

    @Override
    public @NotNull List<Double> getDoubleList(@NotNull String s) {
        return config.getDoubleList(s);
    }

    @Override
    public @NotNull List<Float> getFloatList(@NotNull String s) {
        return config.getFloatList(s);
    }

    @Override
    public @NotNull List<Long> getLongList(@NotNull String s) {
        return config.getLongList(s);
    }

    @Override
    public @NotNull List<Byte> getByteList(@NotNull String s) {
        return config.getByteList(s);
    }

    @Override
    public @NotNull List<Character> getCharacterList(@NotNull String s) {
        return config.getCharacterList(s);
    }

    @Override
    public @NotNull List<Short> getShortList(@NotNull String s) {
        return config.getShortList(s);
    }

    @Override
    public @NotNull List<Map<?, ?>> getMapList(@NotNull String s) {
        return config.getMapList(s);
    }

    @Override
    public <T> @Nullable T getObject(@NotNull String s, @NotNull Class<T> aClass) {
        return config.getObject(s, aClass);
    }

    @Override
    public <T> @Nullable T getObject(@NotNull String s, @NotNull Class<T> aClass, @Nullable T t) {
        return config.getObject(s, aClass, t);
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull String s, @NotNull Class<T> aClass) {
        return config.getSerializable(s, aClass);
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull String s, @NotNull Class<T> aClass, @Nullable T t) {
        return config.getSerializable(s, aClass, t);
    }

    @Deprecated
    @Override
    public @Nullable Vector getVector(@NotNull String s, @Nullable Vector vector) {
        return config.getVector(s, vector);
    }

    @Deprecated
    @Override
    public boolean isVector(@NotNull String s) {
        return config.isVector(s);
    }

    @Deprecated
    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@NotNull String s, @Nullable OfflinePlayer offlinePlayer) {
        return config.getOfflinePlayer(s, offlinePlayer);
    }

    @Deprecated
    @Override
    public boolean isOfflinePlayer(@NotNull String s) {
        return config.isOfflinePlayer(s);
    }

    @Deprecated
    @Override
    public @Nullable ItemStack getItemStack(@NotNull String s, @Nullable ItemStack itemStack) {
        return config.getItemStack(s, itemStack);
    }

    @Deprecated
    @Override
    public boolean isItemStack(@NotNull String s) {
        return config.isItemStack(s);
    }

    @Deprecated
    @Override
    public @Nullable Color getColor(@NotNull String s, @Nullable Color color) {
        return config.getColor(s, color);
    }

    @Deprecated
    @Override
    public boolean isColor(@NotNull String s) {
        return config.isColor(s);
    }

    @Override
    @Deprecated
    public @Nullable Location getLocation(@NotNull String s, @Nullable Location location) {
        return config.getLocation(s, location);
    }

    @Override
    @Deprecated
    public boolean isLocation(@NotNull String s) {
        return config.isLocation(s);
    }

    @Override
    public @Nullable ConfigurationSection getConfigurationSection(@NotNull String s) {
        return config.getConfigurationSection(s);
    }

    @Override
    public boolean isConfigurationSection(@NotNull String s) {
        return config.isConfigurationSection(s);
    }

    @Override
    public @Nullable ConfigurationSection getDefaultSection() {
        return config.getDefaultSection();
    }

    @Override
    public void addDefault(@NotNull String s, @Nullable Object o) {
        config.addDefault(s, o);
    }

    @Override
    public @NotNull List<String> getComments(@NotNull String s) {
        return config.getComments(s);
    }

    @Override
    public @NotNull List<String> getInlineComments(@NotNull String s) {
        return config.getInlineComments(s);
    }

    @Override
    public void setComments(@NotNull String s, @Nullable List<String> list) {
        config.setComments(s, list);
    }

    @Override
    public void setInlineComments(@NotNull String s, @Nullable List<String> list) {
        config.setInlineComments(s, list);
    }

    /**
     * Builds out a memory section
     */
    public static class Builder {
        private final MemoryConfiguration config;

        public Builder() {
            this.config = new MemoryConfiguration();
        }

        public Builder set(String path, Object value) {
            config.set(path, value);
            return this;
        }

        public Config build() {
            return new Config(config);
        }
    }
}
