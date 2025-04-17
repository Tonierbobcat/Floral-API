package com.loficostudios.floralcraftapi.player;

import com.cjcrafter.vivecraft.VSE;
import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.profile.AbstractProfileManager;
import com.loficostudios.floralcraftapi.profile.impl.ProfileData;
import com.loficostudios.floralcraftapi.world.FloralWorld;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FloralPlayer extends AbstractPlayer<ProfileData> {

    public FloralPlayer(@NotNull Player player) {
        super(player, getOrCreateProfile(player));
    }

    public boolean inVR() {
        var vr = VSE.vivePlayers.get(getUniqueId());
        return vr != null && vr.isVR();
    }

    public boolean inCombat() {
        return FloralCraftAPI.inst().getCombatManager().inCombat(bukkit());
    }

    public boolean inPvP() {
        return FloralCraftAPI.inst().getCombatManager().inPvP(bukkit());
    }

    public boolean isClaimBypassEnabled() {
        return Objects.requireNonNullElse(getPersistentDataContainer().get(Keys.CLAIM_BYPASS, PersistentDataType.BOOLEAN), false);
    }

    public void Travel(Location location, long delay) {
        FloralCraftAPI.inst().getTravelManager().travel(bukkit(), location, delay);
    }

    public void Travel(FloralWorld world, long delay) {
        FloralCraftAPI.inst().getTravelManager().travel(bukkit(), world, delay);
    }

    public void setClaimBypassEnabled(boolean claimBypassEnabled) {
        var pdc = getPersistentDataContainer();

        if (!claimBypassEnabled) {
            pdc.remove(Keys.CLAIM_BYPASS);
        } else {
            pdc.set(Keys.CLAIM_BYPASS, PersistentDataType.BOOLEAN, true);
        }
    }

    private static ProfileData getOrCreateProfile(@NotNull Player player) {
        AbstractProfileManager<ProfileData> profileManager = FloralCraftAPI.inst().getProfileManager();
        ProfileData profile = profileManager.getProfile(player);
        return (profile != null) ? profile : profileManager.createProfile(player);
    }

    public static class Keys {
        public static final NamespacedKey CLAIM_BYPASS = FloralCraftAPI.getNamespaceKey("claim_bypass");
    }
}
