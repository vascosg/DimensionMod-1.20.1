package net.agentefreitas.dimensionmod.enchantments;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.custom.LifeStealEnchantment;
import net.agentefreitas.dimensionmod.enchantments.custom.OneThousandStepArtEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DimensionMod.MOD_ID);

    public static final RegistryObject<Enchantment> LIFE_STEAL = ENCHANTMENTS.register("life_steal",
            () -> new LifeStealEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> ONE_THOUSAND_STEP_ART = ENCHANTMENTS.register("one_thousand_step_art",
            () -> new OneThousandStepArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, EquipmentSlot.FEET));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
