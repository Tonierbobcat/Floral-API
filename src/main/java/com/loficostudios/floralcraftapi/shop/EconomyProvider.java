package com.loficostudios.floralcraftapi.shop;

import org.bukkit.OfflinePlayer;

public interface EconomyProvider {
    boolean has(OfflinePlayer player, double amount);
    boolean withdrawalPlayer(OfflinePlayer player, double amount);
    boolean depositPlayer(OfflinePlayer player, double amount);
}
