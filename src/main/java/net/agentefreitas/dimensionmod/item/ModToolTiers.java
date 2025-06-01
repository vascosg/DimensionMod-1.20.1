package net.agentefreitas.dimensionmod.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;


public class ModToolTiers {

    public static final Tier FIVE_ELEMENT_TIER = new Tier() {
        @Override
        public int getUses() { return Integer.MAX_VALUE; }  // Super alta durabilidade
        @Override
        public float getSpeed() { return 12.0F; }
        @Override
        public float getAttackDamageBonus() { return 8.0F; }
        @Override
        public int getLevel() { return 4; }
        @Override
        public int getEnchantmentValue() { return 100; }  // Encantabilidade extrema
        @Override
        public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
    };

}
