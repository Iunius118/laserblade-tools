package com.github.iunius118.laserbladetools.item;

import com.github.iunius118.laserbladetools.tags.ModBlockTags;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import com.google.common.base.Suppliers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ModTiers implements Tier {
    LASER_BLADE(
            ModBlockTags.INCORRECT_FOR_LASER_BLADE_TOOL,
            Tiers.DIAMOND.getUses() + Tiers.IRON.getUses() * 3,
            Tiers.DIAMOND.getSpeed(),
            Tiers.DIAMOND.getAttackDamageBonus(),
            Tiers.IRON.getEnchantmentValue(),
            () -> Ingredient.of(ModItemTags.LASER_BLADE_TOOL_MATERIALS));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModTiers(TagKey<Block> incorrectBlockForDrops, int uses, float speed, float damage, int enchantmentValue,
             Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlockForDrops;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
