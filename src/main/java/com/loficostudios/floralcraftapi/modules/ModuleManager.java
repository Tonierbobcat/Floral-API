package com.loficostudios.floralcraftapi.modules;

import com.loficostudios.floralcraftapi.FloralPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;

public class ModuleManager {
    protected final HashMap<Class<? extends Module>, Module> modules = new HashMap<>();

    private final FloralPlugin plugin;

    public ModuleManager(FloralPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> ModuleConsumer<T> register(Class<T> clazz) throws IllegalArgumentException {
        try {
            Method method = clazz.getDeclaredMethod("initialize", JavaPlugin.class);
            Object result = method.invoke(null, plugin); // static method: instance = null
            return consumer -> consumer.accept(((T) result));
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to initialize module: " + clazz.getName(), e);
        }
    }

    public <T extends Module> @Nullable T get(Class<T> clazz) {
        var obj = modules.get(clazz);
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        return null;
    }
}
