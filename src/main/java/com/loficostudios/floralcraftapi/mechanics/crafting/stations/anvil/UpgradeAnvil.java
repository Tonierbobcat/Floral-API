package com.loficostudios.floralcraftapi.mechanics.crafting.stations.anvil;

import com.loficostudios.floralcraftapi.FloralCraftAPI;
import com.loficostudios.floralcraftapi.config.Config;
import com.loficostudios.floralcraftapi.items.ItemRarity;
import com.loficostudios.floralcraftapi.player.FloralPlayer;
import com.loficostudios.floralcraftapi.profile.components.resources.Resource;
import com.loficostudios.floralcraftapi.utils.Debug;
import com.loficostudios.floralcraftapi.utils.ResourceEconomy;
import com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems.MMOItemExtensions;
import com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems.UpgradeWeaponResult;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class UpgradeAnvil {

    private final HashMap<String, ResourceRequirement> requirementsPerType;

    public UpgradeAnvil(HashMap<String, UpgradeAnvil.ResourceRequirement> requirementsPerType) {
        this.requirementsPerType = requirementsPerType;
    }

    public boolean upgradable(@NotNull ItemStack itemstack) {
        return upgradable(NBTItem.get(itemstack));
    }

    public boolean upgradable(@NotNull NBTItem item) {
        var type = item.getType();
        var rarity = MMOItemExtensions.getRarity(item);

        if (rarity == null) {
            Debug.logError("Rarity is null");
            return false;
        }
        if (type == null) {
            Debug.log("Type is null. Upgrade");
            return false;
        }
        var requirement = requirementsPerType.get(type.toUpperCase());
        if (requirement == null) {
            Debug.log("Requirement is null. Upgrade");
            return false;
        }
        if (!MMOItemExtensions.canUpgrade(item).isSuccess()) {
            Debug.log("Cannot upgrade");
            return false;
        }
        return true;
    }

    public int getRequiredEssence(ItemStack item) {
        return getRequiredEssence(NBTItem.get(item));
    }

    public @Nullable ResourceRequirement getUpgradeRequirement(NBTItem item) {
        var type = item.getType();
        if (type == null)
            return null;
        return getRequirement(type.toUpperCase());
    }
    public ResourceRequirement getRequirement(String weaponType) {
        return requirementsPerType.get(weaponType);
    }

    public @NotNull Resource getEssenceResourceFromRarity(ItemRarity rarity) throws IllegalArgumentException {
        Resource resource = null;
        switch (rarity) {
            case COMMON -> resource = Resource.valueOf("COMMON_ESSENCE");
            case UNCOMMON -> resource = Resource.valueOf("UNCOMMON_ESSENCE");
            case RARE -> resource = Resource.valueOf("RARE_ESSENCE");
            case EPIC -> resource = Resource.valueOf("EPIC_ESSENCE");
            case LEGENDARY -> resource = Resource.valueOf("LEGENDARY_ESSENCE");
            case MYTHICAL -> resource = Resource.valueOf("MYTHICAL_ESSENCE");
            case STARLIGHT -> resource = Resource.valueOf("STARLIGHT_ESSENCE");
            default -> throw new IllegalArgumentException("Unknown rarity: " + rarity);
        }
        return resource;
    }

    public int getRequiredEssence(NBTItem item) {
        var rarity = MMOItemExtensions.getRarity(item);
        if (rarity == null)
            return -1;
        var mmo = new LiveMMOItem(item);
        var tier = rarity.numeral();
        return 1000 * tier * mmo.getUpgradeLevel();
    }

    public @NotNull UpgradeAnvilResult upgrade(FloralPlayer player, ItemStack itemstack, boolean bypass) {
        var nbt = NBTItem.get(itemstack);
        if (!upgradable(nbt)) {
            return new UpgradeAnvilResult(null, UpgradeAnvilResult.Type.NOT_UPGRADABLE);
        }

        var mmo = new LiveMMOItem(nbt);
        var rarity = Objects.requireNonNull(MMOItemExtensions.getRarity(nbt));
        var currentLevel = mmo.getUpgradeLevel();
        var type = Objects.requireNonNull(nbt.getType());
        var requirement = Objects.requireNonNull(requirementsPerType.get(type.toUpperCase()));

        var essence = getEssenceResourceFromRarity(rarity);
        var required = getRequiredEssence(nbt);

        Debug.log("Essence: " + essence + " Required: " + required);
        if (!bypass) {
            if (!ResourceEconomy.has(player, essence, required)) {
                return new UpgradeAnvilResult(null, UpgradeAnvilResult.Type.NOT_ENOUGH_ESSENCE);
            }
            ResourceEconomy.remove(player, essence, required);
        }

        if (!bypass && !requirement.remove(player, currentLevel, rarity.numeral())) {
            return new UpgradeAnvilResult(null, UpgradeAnvilResult.Type.NOT_ENOUGH_RESOURCES);
        }

        var result = applyUpgrade(nbt);
        Debug.log("Result: " + result.type());
        if (!result.isSuccess()) {
            return new UpgradeAnvilResult(null, UpgradeAnvilResult.Type.FAILURE);
        }
        return new UpgradeAnvilResult(result.item(), UpgradeAnvilResult.Type.SUCCESS);
    }

    public @NotNull UpgradeAnvilResult upgrade(FloralPlayer player, ItemStack itemstack) {
        return upgrade(player, itemstack, false);
    }

    public @NotNull UpgradeAnvilResult upgrade(Player player, ItemStack itemstack) {
        return upgrade(player, itemstack, false);
    }

    public @NotNull UpgradeAnvilResult upgrade(Player player, ItemStack itemstack, boolean bypass) {
        return upgrade(FloralPlayer.get(player), itemstack, bypass);
    }

    private UpgradeWeaponResult applyUpgrade(NBTItem item) {
        var mmo = new LiveMMOItem(item);

        var current = mmo.getUpgradeLevel();
        var max = mmo.getMaxUpgradeLevel();
        if (max > 0 && current >= max)
            return new UpgradeWeaponResult(null, UpgradeWeaponResult.Type.MAX_LEVEL);
        return MMOItemExtensions.upgradeWeapon(item, current + 1);
    }

    public static class ResourceRequirement {

        private final int arcaneShards;
        private final int gaiaCrystals;
        private final int titaniteIngots;
        private final int electrumBatteries;

        public ResourceRequirement(int arcaneShards, int gaiaCrystals, int titaniteIngots, int electrumBatteries) {
            this.arcaneShards = arcaneShards;
            this.gaiaCrystals = gaiaCrystals;
            this.titaniteIngots = titaniteIngots;
            this.electrumBatteries = electrumBatteries;
        }

        public int getBaseArcaneShards() {
            return arcaneShards;
        }

        public int getBaseGaiaCrystals() {
            return gaiaCrystals;
        }

        public int getBaseTitaniteIngots() {
            return titaniteIngots;
        }

        public int getBaseElectrumBatteries() {
            return electrumBatteries;
        }

        public int calculateArcaneShards(int level, int tier) {
            return (int) (arcaneShards * Math.pow(1.2, level * tier));
        }
        public int calculateGaiaCrystals(int level, int tier) {
            return (int) (gaiaCrystals * Math.pow(1.2, level * tier));
        }
        public int calculateTitaniteIngots(int level, int tier) {
            return (int) (titaniteIngots * Math.pow(1.2, level * tier));
        }
        public int calculateElectrumBattery(int level, int tier) {
            return (int) (electrumBatteries * Math.pow(1.2, level * tier));
        }

        public boolean has(FloralPlayer player, int level, int tier) {
            var requiredArcaneShards = calculateArcaneShards(level, tier);
            var requiredGaiaCrystals = calculateGaiaCrystals(level, tier);
            var requiredTitaniteIngots = calculateTitaniteIngots(level, tier);
            var requiredElectrumBatteries = calculateElectrumBattery(level, tier);

            return ResourceEconomy.has(player, Resource.ARCANE_SHARD, requiredArcaneShards) &&
                    ResourceEconomy.has(player, Resource.GAIA_CRYSTAL, requiredGaiaCrystals) &&
                    ResourceEconomy.has(player, Resource.TITANITE_INGOT, requiredTitaniteIngots) &&
                    ResourceEconomy.has(player, Resource.ELECTRUM_BATTERY, requiredElectrumBatteries);
        }

        public boolean remove(FloralPlayer player, int level, int tier) {
            if (!has(player, level, tier))
                return false;

            ResourceEconomy.remove(player, Resource.ARCANE_SHARD, calculateArcaneShards(level, tier));
            ResourceEconomy.remove(player, Resource.GAIA_CRYSTAL, calculateGaiaCrystals(level, tier)) ;
            ResourceEconomy.remove(player, Resource.TITANITE_INGOT, calculateTitaniteIngots(level, tier));
            ResourceEconomy.remove(player, Resource.ELECTRUM_BATTERY, calculateElectrumBattery(level, tier));

            return true;
        }

    }
}
