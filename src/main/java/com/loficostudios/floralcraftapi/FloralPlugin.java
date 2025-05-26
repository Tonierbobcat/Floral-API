package com.loficostudios.floralcraftapi;

import com.loficostudios.floralcraftapi.modules.ModuleManager;
import com.loficostudios.floralcraftapi.utils.Debugger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


public class FloralPlugin extends JavaPlugin {
    private final ModuleManager moduleManager = new ModuleManager(this);
    private final Debugger debugger = new Debugger(getName());

    public Debugger getDebugger() {
        return debugger;
    }

    @Deprecated
    @Override
    public @NotNull Logger getLogger() {
        return super.getLogger();
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
