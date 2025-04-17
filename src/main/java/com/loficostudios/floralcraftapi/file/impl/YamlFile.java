/**
 * @Author Tonierbobcat
 * @Github https://github.com/Tonierbobcat
 * @version MelodyApi
 */

package com.loficostudios.floralcraftapi.file.impl;

import com.loficostudios.floralcraftapi.utils.Debug;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YamlFile {

    private FileConfiguration config;

    private File file;

    private final String fileName;

    public YamlFile(final JavaPlugin plugin, final String fileName) {
        this.fileName = fileName;
        create(plugin);
    }

    public YamlFile(final String fileName, final JavaPlugin plugin) {
        this.fileName = fileName;
        create(plugin);
    }

    private void create(final JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            if(plugin.getResource(fileName) == null) {
                try {
                    file.createNewFile();
                } catch (IOException ignore) {
                    Debug.logError("Could not save file!");
                }
            }else {
                plugin.saveResource(fileName, false);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            Debug.logError("Could not save file!");
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
