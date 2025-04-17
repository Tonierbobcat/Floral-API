package com.loficostudios.floralcraftapi.profile.impl;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.events.player.PlayerFirstJoinEvent;
import com.loficostudios.floralcraftapi.profile.IUserData;
import com.loficostudios.floralcraftapi.profile.components.PlayerCharacterInventory;
import com.loficostudios.floralcraftapi.profile.components.PlayerResourceManager;
import com.loficostudios.floralcraftapi.profile.components.PlayerStatManager;
import com.loficostudios.floralcraftapi.profile.components.PlayerVault;
import com.loficostudios.floralcraftapi.profile.components.attributes.PlayerAttributeManager;
import com.loficostudios.floralcraftapi.profile.components.level.PlayerLevelManager;
import com.loficostudios.floralcraftapi.profile.components.mail.MailBox;
import com.loficostudios.floralcraftapi.utils.interfaces.Mappable;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ProfileData implements IUserData, Mappable<Object> {

    private final UUID uuid;
    private final long firstJoin;

    private final OfflinePlayer holder;

    private final MailBox mailBox; //todo add saving & loading
    private final PlayerVault vault;
    private final PlayerResourceManager resources;
    private final PlayerStatManager stats;
    private final PlayerLevelManager level;
    private final PlayerCharacterInventory characterInventory;  //todo add saving & loading
    private final PlayerAttributeManager attributes;

    public ProfileData(OfflinePlayer player) {
        this.level = new PlayerLevelManager(this, FloralCraftAPI.getConfig().playerLeveling(), null);
        this.holder = player;
        this.attributes = new PlayerAttributeManager(this);
        this.vault = new PlayerVault(this);
        this.uuid = player.getUniqueId();
        this.firstJoin = System.currentTimeMillis();
        this.resources = new PlayerResourceManager(this);
        this.stats = new PlayerStatManager(this);
        this.mailBox = new MailBox(this);  //todo add saving & loading
        this.characterInventory = new PlayerCharacterInventory(this);  //todo add saving & loading

        FloralCraftAPI.runTaskLater(() -> {
            var online = Objects.requireNonNull(player.getPlayer());
            var event = new PlayerFirstJoinEvent(online);
            Bukkit.getPluginManager().callEvent(event);
        }, 1);
    }

    @SuppressWarnings("unchecked")
    public ProfileData(OfflinePlayer player, Map<String, Object> data) {
        this.holder = player;
        this.uuid = player.getUniqueId();
        this.firstJoin = (Long) Objects.requireNonNullElse(data.get("first-join"), System.currentTimeMillis());

        this.level = new PlayerLevelManager(this, FloralCraftAPI.getConfig().playerLeveling(), null,
                (Integer) data.get("level"),
                (Integer) data.get("experience"));

        var resources = (Map<String, Map<String, Object>>) data.get("resources");

        this.characterInventory = new PlayerCharacterInventory(this);  //todo add saving & loading

        this.resources = new PlayerResourceManager(this, resources);

        this.stats = new PlayerStatManager(this);

        this.attributes = new PlayerAttributeManager(this, data.get("attributes") != null
                ? (Map<String, Object>) data.get("attributes")
                : null);

        final Map<Integer, String> vault = new HashMap<>();
        for (var entry : ((Map<String, String>) data.get("vault")).entrySet()) {
            String item = entry.getValue();
            Integer slot = Integer.parseInt(entry.getKey());
            vault.put(slot, item);
        }

        this.mailBox = new MailBox(this);  //todo add saving & loading

        this.vault = new PlayerVault(this, vault);
    }

    public @NotNull PlayerResourceManager getResources() {
        return resources;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public OfflinePlayer getHolder() {
        return holder;
    }

    @Override
    public Map<String, Object> toMap() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("uuid", uuid.toString());
        result.put("firstJoin", firstJoin);
        result.put("vault", vault.toMap());
        result.put("resources", resources.toMap());
//        result.put("claim-points", claimPoints);
        result.put("attributes", attributes.toMap());

        result.put("level", level.current());
        result.put("experience", level.getCurrentExperience());

        return result;
    }

    public @NotNull PlayerAttributeManager getAttributes() {
        return attributes;
    }

    public @NotNull PlayerLevelManager getLevels() {
        return level;
    }

    public @NotNull PlayerStatManager getStats() {
        return stats;
    }

    public @NotNull PlayerVault getVault() {
        return this.vault;
    }

    public MMOPlayerData getMMO() {
        return MMOPlayerData.get(holder);
    }

    public @NotNull MailBox getMail() {
        return mailBox;
    }

    @Override
    public PlayerCharacterInventory getCharacterInventory() {
        return characterInventory;
    }
}
