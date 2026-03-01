package net.agentefreitas.dimensionmod.enchantments.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class OneThousandStepArtEnchantment extends Enchantment {

    public OneThousandStepArtEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
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
