package com.loficostudios.floralcraftapi.profile.components.mail;

import com.loficostudios.floralcraftapi.utils.FileUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemMail extends Mail {
    private final String item;

    public ItemMail(OfflinePlayer sender, ItemStack item) {
        super(sender, sender.getName() + " has sent you a gift!");
        this.item = FileUtils.serializeObjectToString(item);
    }

    @Override
    public void receive(Player player) {
        var item = (ItemStack) FileUtils.deserializeStringToObject(this.item, ItemStack.class);
        player.getInventory().addItem(item);
    }
}
