package com.loficostudios.floralcraftapi.items;

import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.FoodProperties;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@SuppressWarnings("UnstableApiUsage")
public record FloralFood(Component name, int model, Material base, int nutrition, float saturation, PotionEffect[] effects) {
    public FloralFood(Component name, int model, Material base, int food, float saturation) {
        this(name, model, base, food, saturation, new PotionEffect[0]);
    }

    public @NotNull ItemStack build(int quality) {
        var helper = new QualifiableItemHelper();
        var item = ItemStack.of(base);

        var meta = item.getItemMeta();
        meta.setCustomModelData(model);
        meta.displayName(name);
        item.setItemMeta(meta);
        var effects = Arrays.stream(this.effects)
                .map(effect -> new PotionEffect(effect.getType(), effect.getDuration() * quality, effect.getAmplifier(), effect.isAmbient(), effect.hasParticles()))
                .toList();

        var consumable = Consumable.consumable()
                .addEffect(ConsumeEffect.applyStatusEffects(effects, 1))
                .build();
        item.setData(DataComponentTypes.CONSUMABLE, consumable);

        var properties = FoodProperties.food()
                .saturation(saturation * quality)
                .nutrition(nutrition * quality)
                .build();
        item.setData(DataComponentTypes.FOOD, properties);

        var nbt = NBTItem.get(item);
        nbt.addTag(new ItemTag(QualifiableItemHelper.QUALITY_STAT, quality));
        return helper.updateLore(nbt);
    }
}