package net.agentefreitas.dimensionmod.enchantments.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EverEvolvingSpiritArtEnchantment extends Enchantment {
    public EverEvolvingSpiritArtEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    // Permite que o encantamento seja aplicado em Espadas, Machados, Picaretas e Arcos
    @Override
    public boolean canEnchant(ItemStack pStack) {
        Item item = pStack.getItem();
        return item instanceof SwordItem || item instanceof BowItem ||
                item instanceof AxeItem || item instanceof PickaxeItem ||
                super.canEnchant(pStack);
    }

    @Override
    public int getMaxLevel() {
        return 1; // Só precisa de um nível, pois ele evolui sozinho
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
