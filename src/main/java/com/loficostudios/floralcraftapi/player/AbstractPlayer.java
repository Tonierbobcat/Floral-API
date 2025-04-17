package com.loficostudios.floralcraftapi.player;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.party.Party;
import com.loficostudios.floralcraftapi.party.player.PartyEntity;
import com.loficostudios.floralcraftapi.profile.DataContainer;
import com.loficostudios.floralcraftapi.profile.IUserData;
import com.loficostudios.floralcraftapi.profile.components.PlayerCharacterInventory;
import com.loficostudios.floralcraftapi.profile.components.PlayerResourceManager;
import com.loficostudios.floralcraftapi.profile.components.PlayerStatManager;
import com.loficostudios.floralcraftapi.profile.components.PlayerVault;
import com.loficostudios.floralcraftapi.profile.components.attributes.PlayerAttributeManager;
import com.loficostudios.floralcraftapi.profile.components.level.PlayerLevelManager;
import com.loficostudios.floralcraftapi.profile.components.mail.MailBox;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPlayer<T extends IUserData> extends PlayerExtension implements PartyEntity, DataContainer {

    private final T data;

    public AbstractPlayer(@NotNull Player player, @NotNull T data) {
        super(player);
        Validate.isTrue(data != null, "Data cannot be null!");
        this.data = data;
    }

    @Override
    public int getLevel() {
        return this.data.getLevels().current();
    }
    @Override
    public void setLevel(int level) {
        this.data.getLevels().setLevel(level);
    }

    public void addExperience(int v) {
        this.data.getLevels().addExperience(v);
    }

    @Override
    public void setExperience(int ex) {
        super.setExperience(ex); //todo
    }

    public int getExperience() {
        return this.data.getLevels().getCurrentExperience();
    }
    public int getExperienceToNextLevel() {
        return this.data.getLevels().getExperienceToNextLevel();
    }

    @Override
    @Deprecated
    public LivingEntity getBukkitEntity() {
        return bukkit();
    }

    @Override
    @Deprecated
    public Optional<Player> getPlayer() {
        return Optional.of(bukkit());
    }

    @Override
    public Party getCurrentParty() {
        var uuid = getPersistentDataContainer().get(FloralCraftAPI.getNamespaceKey("party"), PersistentDataType.STRING);
        if (uuid != null) {
            try {
                return FloralCraftAPI.inst().getPartyManager().getParty(UUID.fromString(uuid));
            } catch (IllegalArgumentException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    @Deprecated
    public @NotNull PlayerLevelManager getLevels() {
        return data.getLevels();
    }

    @Override
    public PlayerCharacterInventory getCharacterInventory() {
        return data.getCharacterInventory();
    }

    @Override
    public @Nullable Party setCurrentParty(@Nullable Party party) {
        Validate.isTrue(party == null ||  party.isMember(getUniqueId()), "Player is not member!");
        var key = FloralCraftAPI.getNamespaceKey("party");
        var last = getCurrentParty();
        var pdc = getPersistentDataContainer();
        if (party != null) {
            pdc.set(key, PersistentDataType.STRING, party.getUniqueId().toString());
        } else {
            pdc.remove(key);
        }

        return last;
    }

    @Override
    public @NotNull PlayerAttributeManager getAttributes() {
        return data.getAttributes();
    }

    @Override
    public @NotNull MailBox getMail() {
        return data.getMail();
    }

    @Override
    public @NotNull PlayerResourceManager getResources() {
        return data.getResources();
    }

    @Override
    public @NotNull PlayerStatManager getStats() {
        return data.getStats();
    }

    @Override
    public @NotNull PlayerVault getVault() {
        return data.getVault();
    }

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return bukkit().getPersistentDataContainer();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return bukkit().getUniqueId();
    }

    @Override
    public @NotNull String getName() {
        return bukkit().getName();
    }

    @Override
    public @NotNull Location getLocation() {
        return bukkit().getLocation();
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        return bukkit().teleport(location);
    }

    @Override
    public boolean isDead() {
        return bukkit().isDead();
    }
}
