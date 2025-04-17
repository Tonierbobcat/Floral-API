package com.loficostudios.floralcraftapi.file;

import com.loficostudios.floralcraftapi.file.impl.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public abstract class AutoConfig {
    private final String name;

    public AutoConfig(String name) {
        this.name = name;
    }

    public void loadData(Class<?> clazz, JavaPlugin plugin) {
        try {
            var file = new YamlFile(name, plugin);
            var config = file
                    .getConfig();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Path path = field.getAnnotation(Path.class);

                if (path == null)
                    continue;
                String pathValue = path.value();

                Object configValue = config.get(pathValue);

                if (configValue != null) {
                    field.setAccessible(true);
                    field.set(null, configValue);
                } else {
                    config.set(pathValue, field.get(null));
                }
            }
            file.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
