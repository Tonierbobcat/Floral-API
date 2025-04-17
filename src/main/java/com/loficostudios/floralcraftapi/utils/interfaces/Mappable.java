package com.loficostudios.floralcraftapi.utils.interfaces;

import java.util.Map;

public interface Mappable<Impl> {
    Map<String, Impl> toMap();
}
