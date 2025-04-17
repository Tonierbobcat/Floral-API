package com.loficostudios.floralcraftapi.utils;

@Deprecated
public class Debug {
    private static final Debugger debug;

    public static void log(Object object) {
        debug.log(object.toString());
    }

    public static void log(String message) {
        debug.log(message);
    }
    public static void logWarning(String message) {
        debug.logWarning(message);
    }
    public static void logError(String message) {
        debug.logError(message);
    }

    static {
        debug = new Debugger("FloralCraftAPI");
        debug.setEnabled(true);
    }
}
