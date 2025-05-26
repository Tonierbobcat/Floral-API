package com.loficostudios.floralcraftapi.player;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.party.player.PartyMember;
import com.loficostudios.floralcraftapi.profile.DataContainer;
import com.loficostudios.floralcraftapi.profile.PlayerProfile;
import com.loficostudios.floralcraftapi.profile.components.PlayerCharacterInventory;
import com.loficostudios.floralcraftapi.profile.components.PlayerResources;
import com.loficostudios.floralcraftapi.profile.components.PlayerStats;
import com.loficostudios.floralcraftapi.profile.components.PlayerVault;
import com.loficostudios.floralcraftapi.profile.components.attributes.PlayerAttributes;
import com.loficostudios.floralcraftapi.profile.components.level.PlayerLevelManager;
import com.loficostudios.floralcraftapi.profile.components.mail.MailBox;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface FloralPlayer extends PartyMember, DataContainer {
    boolean inVR();

    boolean inCombat();

    boolean inPvP();

    void travel(Location location, long delay);

    void travel(FloralWorld world);

    @Override
    default int getLevel() {
        return getProfile().getLevels().current();
    }

    default void setLevel(int level) {
        getProfile().getLevels().setLevel(level);
    }

    default void addExperience(int v) {
        getProfile().getLevels().addExperience(v);
    }

    default void sendMessage(String message) {
        bukkit().sendMessage(message);
    }

    default void sendMessage(@NotNull Component component) {
        bukkit().sendMessage(component);
    }

    default void setExperience(int ex) {
        getProfile().getLevels().addExperience(ex); //todo change this to set
    }

    default int getExperience() {
        return getProfile().getLevels().getCurrentExperience();
    }
    default int getExperienceToNextLevel() {
        return getProfile().getLevels().getExperienceToNextLevel();
    }

    /**
     * Base should only be retrived if you need to get the org.bukkit.entity.Player
     * Just use the methods that are in player extention
     * @return
     */
    default Player bukkit() {
        return ((Player) getBukkitEntity());
    }

    default void playSound(@NotNull Location source, @NotNull Sound sound, SoundCategory category, float volume, float pitch) {
        bukkit().playSound(source, sound, category, volume, pitch);
    }
    default void playSound(@NotNull Location source, @NotNull  Sound sound, float volume, float pitch) {
        playSound(source, sound, SoundCategory.MASTER, volume, pitch);
    }
    default void playSound(@NotNull Entity source, @NotNull  Sound sound, float volume, float pitch) {
        playSound(source.getLocation(), sound, volume, pitch);
    }
    default void playSound(@NotNull Sound sound, float volume, float pitch) {
        playSound(getLocation(), sound, volume, pitch);
    }

    @Override
    @Deprecated
    default @NotNull PlayerLevelManager getLevels() {
        return getProfile().getLevels();
    }

    @Override
    default PlayerCharacterInventory getCharacterInventory() {
        return getProfile().getCharacterInventory();
    }

    @NotNull PlayerProfile getProfile();

    @Override
    default @NotNull PlayerAttributes getAttributes() {
        return getProfile().getAttributes();
    }

    @Override
    default @NotNull MailBox getMail() {
        return getProfile().getMail();
    }

    @Override
    default @NotNull PlayerResources getResources() {
        return getProfile().getResources();
    }

    @Override
    default @NotNull PlayerStats getStats() {
        return getProfile().getStats();
    }

    @Override
    default @NotNull PlayerVault getVault() {
        return getProfile().getVault();
    }

    @Override
    default @NotNull PersistentDataContainer getPersistentDataContainer() {
        return getBukkitEntity().getPersistentDataContainer();
    }

    @Override
    default @NotNull UUID getUniqueId() {
        return getBukkitEntity().getUniqueId();
    }

    @Override
    default @NotNull String getName() {
        return getBukkitEntity().getName();
    }

    @Override
    default @NotNull Location getLocation() {
        return getBukkitEntity().getLocation();
    }

    @Override
    default boolean teleport(@NotNull Location location) {
        return getBukkitEntity().teleport(location);
    }

    @Override
    default boolean isDead() {
        return getBukkitEntity().isDead();
    }

    static FloralPlayer get(@NotNull Player player) {
        return FloralCraftAPI.unnamed.getPlayer(player);
    }
}
