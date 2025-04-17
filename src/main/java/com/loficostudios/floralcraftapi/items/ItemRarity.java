package com.loficostudios.floralcraftapi.items;

import com.loficostudios.floralcraftapi.utils.Debug;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ItemRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY,
    MYTHICAL,
    STARLIGHT,
    EXOTIC;

    public int numeral() {
        return this.ordinal() + 1;
    }

    public static @Nullable ItemRarity from(ItemStack item) {
        var tier = ItemTier.ofItem(NBTItem.get(item));
        if (tier != null) {

            try {
                return ItemRarity.valueOf(tier.getId().toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        return switch (item.getType()) {
            case WOODEN_SWORD: case WOODEN_PICKAXE: case WOODEN_SHOVEL: case WOODEN_AXE: case WOODEN_HOE:
                yield COMMON;
            case GOLDEN_SWORD: case GOLDEN_PICKAXE: case GOLDEN_SHOVEL: case GOLDEN_AXE: case GOLDEN_HOE:
                yield UNCOMMON;
            case STONE_SWORD: case STONE_PICKAXE: case STONE_SHOVEL: case STONE_AXE: case STONE_HOE:
                yield RARE;
            case IRON_SWORD: case IRON_PICKAXE: case IRON_SHOVEL: case IRON_AXE: case IRON_HOE:
                yield EPIC;
            case DIAMOND_SWORD: case DIAMOND_PICKAXE: case DIAMOND_SHOVEL: case DIAMOND_AXE: case DIAMOND_HOE:
                yield LEGENDARY;
            case NETHERITE_SWORD: case NETHERITE_PICKAXE: case NETHERITE_SHOVEL: case NETHERITE_AXE: case NETHERITE_HOE:
                yield MYTHICAL;
            default:
                yield null;
        };
    }

    @Deprecated
    public static ItemRarity valueOf(@NotNull NBTItem item) {
        var type = item.getType();
        if (type == null)
            throw new IllegalArgumentException("Item is not an MMOItem!");
        var mmo = new LiveMMOItem(item);
        var tier = mmo.getTier();
        Debug.log("(ItemRarity#valueOf(NBTItem)) ItemTier: " + (tier != null ? tier.getId() : "null"));
        if (tier == null)
            throw new IllegalArgumentException("MMOItem does not have an ItemTier");
        return valueOf(tier.getId().toUpperCase());
    }
}
