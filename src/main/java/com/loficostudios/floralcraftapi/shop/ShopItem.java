package com.loficostudios.floralcraftapi.shop;

import com.loficostudios.floralcraftapi.gui.guiicon.GuiIcon;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.BiConsumer;

public interface ShopItem<Impl extends ShopItem<Impl>> {

    ShopTransactionResult<Impl> onBuy(ShopInstance<Impl> instance, int amount, Player player);

    String getName();

    GuiIcon getIcon(ShopInstance<?> instance, int amount, BiConsumer<Player, ClickType> onClick);
}
