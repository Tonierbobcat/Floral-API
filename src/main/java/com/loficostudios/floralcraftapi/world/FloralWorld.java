package com.loficostudios.floralcraftapi.world;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public interface FloralWorld extends Listener {

    String SETTINGS_FILE = "floral-world.json";

    /**
     * updates every tick
     */
    void update();

    World getBase();

    List<WorldEvent> getEvents();

    Settings getSettings();

    Vector getCenter();

    boolean isActive();

    default Collection<Player> getPlayers() {
        return getBase().getPlayers();
    }

    default String getName() {
        return getBase().getName();
    }

    default String getDisplayName() {
        return getSettings().get(FloralWorld.Settings.Type.DISPLAY_NAME)
                    .orElseGet(this::getName);
    }

    default boolean hasDisplayName() {
        return getSettings().get(FloralWorld.Settings.Type.DISPLAY_NAME).isPresent();
    }

    default TeleportResult teleport(Player player) {
        return FloralCraftAPI.inst().getWorldManager().getProvider().teleport(player, getBase());
    }

    default CompletableFuture<TeleportResult> teleportAsync(Player player) {
        return FloralCraftAPI.inst().getWorldManager().getProvider().teleportAsync(player, getBase());
    }

    default Location getSpawnLocation() {
        return FloralCraftAPI.inst().getWorldManager().getProvider().getSpawn(getBase());
    }

    default boolean inWorld(Player player) {
        return inWorld((Entity) player);
    }

    default boolean inWorld(FloralPlayer player) {
        return inWorld(player.bukkit());
    }

    default boolean inWorld(Entity entity) {
        return inWorld(entity.getLocation());
    }

    default boolean inWorld(Location location) {
        if (getBase() == null)
            return false;
        return location.getWorld().getName().equals(getBase().getName());
    }

    /**
     *
     * @return the most basic usage of floral world with settings loaded from optional config file
     */
    static FloralWorld from(World world) {
        var file = new File(world.getWorldFolder(), SETTINGS_FILE);
        return from(world, new Settings(file));
    }

    /**
     *
     * @return the most basic usage of floral world
     */
    static FloralWorld from(World world, @NotNull Settings settings) {
        return new FloralWorld() {
            @Override
            public void update() {

            }

            @Override
            public Collection<Player> getPlayers() {
                return getBase().getPlayers();
            }

            @Override
            public String getName() {
                return world.getName();
            }

            @Override
            public Vector getCenter() {
                return world.getSpawnLocation().toVector();
            }

            @Override
            public World getBase() {
                return world;
            }

            @Override
            public List<WorldEvent> getEvents() {
                return List.of();
            }

            @Override
            public Settings getSettings() {
                return settings;
            }

            @Override
            public boolean isActive() {
                return true;
            }
        };
    }

    class Settings {

        private final Map<String, Object> objects;
        private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        private final File file;

        public Settings(World parent) {
            this(new File(parent.getWorldFolder(), FloralWorld.SETTINGS_FILE));
        }

        public Settings(File file) {
            this.file = file;

            var objects = new HashMap<String, Object>();

//            try {
//                if (!file.exists()) {
//                    var created = file.createNewFile();
//                }
//                try (FileReader reader = new FileReader(file)) {
//                    objects = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
//                } catch (IOException | NullPointerException ignore) {
//                }
//
//                objects = (objects != null) ? objects : new HashMap<>();
//            } catch (IOException | JsonIOException | JsonSyntaxException e) {
//                e.printStackTrace();
//            }

            this.objects = objects;
        }

//        @Deprecated
//        public Settings(ConfigurationSection config) {
//            Validate.isTrue(config != null, "Config is null!");
//            for (String key : config.getKeys(false)) {
//                objects.put(key, config.get(key));
//            }
//        }

        @Deprecated
        public <T> Optional<T> get(String path, Class<T> clazz) {
            var obj = objects.get(path);
            if (!clazz.isInstance(obj))
                return Optional.empty();
            return Optional.of(clazz.cast(obj));
        }

        @Deprecated
        public Optional<Object> get(String path) {
            return Optional.ofNullable(objects.get(path));
        }

        public <T> Optional<T> get(Type<T> type) {
            var obj = objects.get(type.path);
            var clazz = type.clazz;
            if (!clazz.isInstance(obj))
                return Optional.empty();
            return Optional.of(clazz.cast(obj));
        }

        public void set(String path, Object object) {
            objects.put(path, object);
            save();
        }

        public <T> void set(Type<T> type, T object) {
            objects.put(type.path, object);
            save();
        }

        public void save() {
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(objects, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static class Type<T> {
            public static final Type<String> DISPLAY_NAME = new Type<>("display-name", String.class);
            public static final Type<List<String>> DESCRIPTION = new Type<>("description", new TypeToken<List<String>>() {}.getType());
            public static final Type<String> ICON_TEXTURE = new Type<>("icon-texture", String.class);
            public static final Type<Vector> SPAWN_LOCATION = new Type<>("spawn-location", Vector.class);

            private final String path;
            private final Class<T> clazz;

            public Type(String path, Class<T> clazz) {
                this.path = path;
                this.clazz = clazz;
            }

            public Type(String path, Object obj) {
                this.path = path;
                this.clazz = (Class<T>) obj.getClass();
            }
        }
    }
}
