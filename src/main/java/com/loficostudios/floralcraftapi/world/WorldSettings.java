package com.loficostudios.floralcraftapi.world;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loficostudios.floralcraftapi.file.TypedDataContainer;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorldSettings implements TypedDataContainer {

    private static final String settingsFile = "floral-world.json";

    private final File file;
    private Map<String, Object> objects = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void read(File file) {
        this.objects = new HashMap<>();
    }

    @Override
    public void write(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(objects, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> Optional<T> get(Type<T> type) {
        var obj = objects.get(type.getPath());
        var clazz = type.getClazz();
        if (!clazz.isInstance(obj))
            return Optional.empty();
        return Optional.of(clazz.cast(obj));
    }

    @Override
    public void set(String path, Object object) {
        objects.put(path, object);
        write(file);
    }

    @Override
    public <T> void set(Type<T> type, T object) {
        objects.put(type.getPath(), object);
        write(file);
    }

    public WorldSettings(World parent) {
        this.file = new File(parent.getWorldFolder(), settingsFile);
        read(file);
    }
}
