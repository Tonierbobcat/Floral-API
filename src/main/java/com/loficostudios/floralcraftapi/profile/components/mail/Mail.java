package com.loficostudios.floralcraftapi.profile.components.mail;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Mail {
    private final UUID sender;
    private final String message;


    public Mail(OfflinePlayer sender, String message) {
        this.sender = sender.getUniqueId();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void receive(Player player) {
    }

    public OfflinePlayer getSender() {
        return Bukkit.getOfflinePlayer(sender);
    }
}
