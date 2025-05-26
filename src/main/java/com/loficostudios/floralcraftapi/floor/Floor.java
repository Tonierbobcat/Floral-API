package com.loficostudios.floralcraftapi.floor;

import com.loficostudios.floralcraftapi.dungeon.DungeonTemplate;
import com.loficostudios.floralcraftapi.floor.portal.DungeonPortal;
import com.loficostudios.floralcraftapi.shop.Shop;
import com.loficostudios.floralcraftapi.shop.ShopItem;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import com.loficostudios.floralcraftapi.world.Unlockable;

import java.util.Collection;

public interface Floor extends FloralWorld, Unlockable {
    String getId();
    Collection<DungeonPortal> getDungeonPortals();
    Collection<FloorMilestone> getMilestones();

    <T extends ShopItem<T>> Shop<T> getShopData(String id, Class<T> clazz);

    DungeonTemplate getMainDungeon();
}
