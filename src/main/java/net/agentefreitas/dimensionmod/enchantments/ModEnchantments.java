package net.agentefreitas.dimensionmod.enchantments;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.custom.*;
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

    public static final RegistryObject<Enchantment> CLEAR_VISION_ART = ENCHANTMENTS.register("clear_vision_art",
            () -> new ClearVisionArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static final RegistryObject<Enchantment> ENDLESS_STARING_EYE_ART = ENCHANTMENTS.register("endless_staring_eye_art",
            () -> new EndlessStaringEyeArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static final RegistryObject<Enchantment> BLURRY_FATE_ART_ENCHANTMENT = ENCHANTMENTS.register("blurry_fate_art",
            () -> new BlurryFateArtEnchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.ARMOR, // Categoria para qualquer armadura
                    EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));// Todos os slots

    public static final RegistryObject<Enchantment> LIGHTNING_SWORD_AURA_ART = ENCHANTMENTS.register("lightning_sword_aura_art",
            () -> new LightningSwordAuraArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> BODY_ENSLAVEMENT_ART_ENCHANTMENT = ENCHANTMENTS.register("body_enslavement_art",
            () -> new BodyEnslavementArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> EVER_EVOLVING_SPIRIT_ART_ENCHANTMENT = ENCHANTMENTS.register("ever_evolving_spirit_art",
            () -> new EverEvolvingSpiritArtEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
