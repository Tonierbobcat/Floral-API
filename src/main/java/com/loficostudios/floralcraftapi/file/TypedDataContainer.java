package com.loficostudios.floralcraftapi.file;

import java.io.File;
import java.util.Optional;

public interface TypedDataContainer {
    void read(File file);

    void write(File file);

    <T> Optional<T> get(Type<T> type);

    void set(String path, Object object);

    <T> void set(Type<T> type, T object);

    class Type<T> {
        private final String path;
        private final Class<T> clazz;

        public Type(String path, Class<T> clazz) {
            this.path = path;
            this.clazz = clazz;
        }

        public Type(String path, Object obj) {
            this.path = path;
            this.clazz = (Class<T>) obj.getClass();
        }

        public String getPath() {
            return path;
        }

        public Class<T> getClazz() {
            return clazz;
        }
    }
}
