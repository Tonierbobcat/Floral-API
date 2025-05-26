package com.loficostudios.floralcraftapi.animation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public interface Animation<T> {
    void update(T obj, float elapsedTime, float delta);
    void start(T obj);
    void stop(T obj);
    boolean isComplete();

    static <U> Animation<U> create(Class<? extends Animation<U>> clazz, Object... cxt) {
        try {
            Animation<U> instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(AnimationContext.class)) {
                    field.setAccessible(true);

                    for (Object ctx : cxt) {
                        if (field.getType().isAssignableFrom(ctx.getClass())) {
                            field.set(instance, ctx);
                            break;
                        }
                    }
                }
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
