package com.loficostudios.floralcraftapi.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Debugger {

    private final String name;
    private boolean enabled = true;

    private final Logger logger;

    public Debugger(@NotNull String name) {
        this.name = name;
        this.logger = Bukkit.getLogger();
    }

    public void log(String message) {
        send(Level.INFO, message);
    }
    public void logWarning(String message) {
        send(Level.WARNING, message);
    }

    public void logError(String message) {
        send(Level.SEVERE, message);
    }

    private void send(Level level, String message) {
        if (!enabled)
            return;
        logger.log(level, String.format("[%s] %s", name, message));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }
}
