package com.loficostudios.floralcraftapi.utils.bukkit.items.mmoitems;

import com.loficostudios.floralcraftapi.items.ItemRarity;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MMOItemExtensions {

    public static UpgradeWeaponResult upgradeWeapon(NBTItem item, int to) {
        var result = MMOItemExtensions.canUpgrade(item);
        if (!result.isSuccess())
            return new UpgradeWeaponResult(null, result);
        var mmo = new LiveMMOItem(item);
        mmo.getUpgradeTemplate().upgradeTo(mmo, to);
        return new UpgradeWeaponResult(mmo.newBuilder().build(), UpgradeWeaponResult.Type.SUCCESS);
    }

    public static UpgradeWeaponResult.Type canUpgrade(NBTItem item) {
        var type = MMOItems.getType(item);
        var id = MMOItems.getID(item);

        var tier = ItemTier.ofItem(item);
        if (type == null || tier == null || id == null)
            return UpgradeWeaponResult.Type.NOT_MMOITEM;
        var mmo = new LiveMMOItem(item);
        if (!mmo.hasUpgradeTemplate())
            return UpgradeWeaponResult.Type.NO_UPGRADE_TEMPLATE;
        return UpgradeWeaponResult.Type.SUCCESS;
    }

    /**
     *
     * @deprecated ItemTier is discouraged for use in this API. Use {@link MMOItemExtensions#getRarity(NBTItem)} instead.
     */
    @Deprecated
    public static @Nullable ItemTier getTier(NBTItem item) {
        return ItemTier.ofItem(item);
    }

    /**
     *
     * @deprecated ItemTier is discouraged for use in this API. Use {@link MMOItemExtensions#getRarity(NBTItem)} instead.
     */
    @Deprecated
    public static @Nullable ItemTier getTier(ItemStack item) {
        return getTier(NBTItem.get(item));
    }

    @SuppressWarnings("deprecation")
    public static @Nullable ItemRarity getRarity(NBTItem item) {
        try {
            return ItemRarity.valueOf(item);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean isMMO(@NotNull ItemStack item) {
        return NBTItem.get(item).getType() != null;
    }
}
