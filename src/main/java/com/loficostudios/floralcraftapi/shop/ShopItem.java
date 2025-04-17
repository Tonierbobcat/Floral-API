package com.loficostudios.floralcraftapi.shop;

import com.loficostudios.floralcraftapi.gui.guiicon.GuiIcon;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.BiConsumer;

public abstract class ShopItem<Impl extends ShopItem<Impl>> {

    public abstract ShopTransactionResult<Impl> onBuy(ShopInstance<Impl> instance, int amount, Player player);

    public abstract String getName();

    public abstract GuiIcon getIcon(ShopInstance<?> instance, int amount, BiConsumer<Player, ClickType> onClick);
}
