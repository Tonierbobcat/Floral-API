package com.loficostudios.floralcraftapi.modules;

import java.util.function.Consumer;

public interface ModuleConsumer<T extends Module> {
    void then(Consumer<T> consumer);
}