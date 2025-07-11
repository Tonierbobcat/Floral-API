package com.loficostudios.floralcraftapi.shop;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public interface Shop<Impl extends ShopItem<Impl>> {
    Collection<ShopInstance<Impl>> getInstances();

    default ShopTransactionResult<Impl> buyItem(Player player, ShopInstance<Impl> listing) {
        return buyItem(player, listing, 1);
    }

    ShopTransactionResult<Impl> buyItem(Player player, ShopInstance<Impl> listing, int amount);

    static <T extends ShopItem<T>> Shop<T> create(Collection<ShopInstance<T>> instances, EconomyProvider provider) {
        return new Shop<>() {
            private final Collection<ShopInstance<T>> shopInstances = new ArrayList<>(instances); // Avoid shadowing

            @Override
            public Collection<ShopInstance<T>> getInstances() {
                return shopInstances;
            }

            @Override
            public ShopTransactionResult<T> buyItem(Player player, ShopInstance<T> instance, int amount) {
                var cost = instance.getCost() * amount;

                if (!provider.has(player, cost)) {
                    return new ShopTransactionResult<>(instance, amount, ShopTransactionResult.Type.NOT_ENOUGH_MONEY);
                }
                if (!provider.withdrawalPlayer(player, cost)) {
                    return new ShopTransactionResult<>(instance, amount, ShopTransactionResult.Type.FAILURE);
                }

                var result = instance.getItem().onBuy(instance, amount, player);
                if (!result.type().equals(ShopTransactionResult.Type.SUCCESS))
                    provider.depositPlayer(player, amount);
                return result;
            }
        };
    }

}
