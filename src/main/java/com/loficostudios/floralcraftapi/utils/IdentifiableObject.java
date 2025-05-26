package com.loficostudios.floralcraftapi.utils;

import java.io.Serializable;

public abstract class IdentifiableObject<T extends Serializable> {
    private final T id;
    public IdentifiableObject(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
