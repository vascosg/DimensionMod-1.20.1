package net.agentefreitas.dimensionmod.enchantments.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EndlessStaringEyeArtEnchantment extends Enchantment {
    public EndlessStaringEyeArtEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... pApplicableSlots) {
        super(rarity, category, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + (pLevel - 1) * 10;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
