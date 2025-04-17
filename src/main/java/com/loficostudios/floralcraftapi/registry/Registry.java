package com.loficostudios.floralcraftapi.registry;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;

public interface Registry<T extends Serializable, V> {
    @Nullable V get(T id);
    boolean register(V object);

    Collection<V> getRegistered();
}
