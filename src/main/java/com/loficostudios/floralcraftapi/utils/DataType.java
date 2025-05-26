package com.loficostudios.floralcraftapi.utils;

import com.google.common.reflect.TypeToken;
import com.loficostudios.floralcraftapi.file.TypedDataContainer;
import org.bukkit.util.Vector;

import java.util.List;

public class DataType {
    public static class Dungeon {
        public static final TypedDataContainer.Type<String> FLOOR_ID = new TypedDataContainer.Type<>("floor-id", String.class);
        public static final TypedDataContainer.Type<String> DUNGEON_ID = new TypedDataContainer.Type<>("floor-id", String.class);

    }
    public static class World {
        public static final TypedDataContainer.Type<String> DISPLAY_NAME = new TypedDataContainer.Type<>("display-name", String.class);
        public static final TypedDataContainer.Type<List<String>> DESCRIPTION = new TypedDataContainer.Type<>("description", new TypeToken<List<String>>() {}.getType());
        public static final TypedDataContainer.Type<String> ICON_TEXTURE = new TypedDataContainer.Type<>("icon-texture", String.class);
        public static final TypedDataContainer.Type<Vector> SPAWN_LOCATION = new TypedDataContainer.Type<>("spawn-location", Vector.class);
    }
}
