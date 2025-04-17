package com.loficostudios.floralcraftapi.world;

import com.loficostudios.floralcraftapi.utils.FileUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class WorldLoader {
    public boolean unloadWorld(World world, boolean save) {
        return Bukkit.unloadWorld(world, save);
    }

    public World load(String name) {
        var existing = Bukkit.getWorld(name);
        if (existing != null)
            return existing;
        return new WorldCreator(name).createWorld();
    }

    public boolean deleteWorld(World world) {
        var folder = world.getWorldFolder();
        if (!unloadWorld(world, false))
            return false;
        return FileUtils.deleteDirectory(folder);
    }

    public @Nullable FloralWorld getNewWorldFromFile(File source, String name) {
        Validate.isTrue(copyWorldFolderIntoWorldContainer(source, name));
        var world = new WorldCreator(name).createWorld();
        if (world == null)
            return null;
        return FloralWorld.from(world);
    }

    public boolean copyWorldFolderIntoWorldContainer(File source, String name) {
        return FileUtils.copyFileStructure(source, new File(Bukkit.getWorldContainer(), name), Arrays.asList("uid.dat", "session.lock"));
    }
}
