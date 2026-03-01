package net.agentefreitas.dimensionmod.item;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS  =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DimensionMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DIMENSION_TAB = CREATIVE_MODE_TABS .register("dimension_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DIMENSION_DOOR_KEY.get()))
                    .title(Component.translatable("creativetab.dimension_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.DIMENSION_DOOR_KEY.get());
                        pOutput.accept(ModBlocks.DIMENSION_DOOR_BLOCK.get());

                        pOutput.accept(ModItems.DIARY_OF_A_CREATOR.get());
                        pOutput.accept(ModItems.BLUE_BOOK.get());
                        pOutput.accept(ModItems.GREEN_BOOK.get());
                        pOutput.accept(ModItems.ORANGE_BOOK.get());
                        pOutput.accept(ModItems.PURPLE_BOOK.get());
                        pOutput.accept(ModItems.RED_BOOK.get());
                        pOutput.accept(ModItems.YELLOW_BOOK.get());
                        pOutput.accept(ModBlocks.DIMENSION_BOOK_LECTERN.get());
                        pOutput.accept(ModBlocks.GUARDIAN_BLOCK.get());
                        pOutput.accept(ModBlocks.GUARDIAN_DOOR_BLOCK.get());

                        pOutput.accept(ModItems.FIRE_TREASURE.get());
                        pOutput.accept(ModItems.WATER_TREASURE.get());
                        pOutput.accept(ModItems.WOOD_TREASURE.get());
                        pOutput.accept(ModItems.EARTH_TREASURE.get());
                        pOutput.accept(ModItems.GOLD_TREASURE.get());

                        pOutput.accept(ModItems.FIVE_ELEMENT_INDESTRUCTIBLE_SWORD.get());
                        pOutput.accept(ModItems.SUN_SWORD.get());
                        pOutput.accept(ModItems.MOON_BOW.get());

                        pOutput.accept(ModItems.DISCIPLE_SPANW_EGG.get());
                        pOutput.accept(ModItems.LITTLE_PURPLE_SPANW_EGG.get());
                        pOutput.accept(ModItems.BAI_YU_SPANW_EGG.get());
                        pOutput.accept(ModItems.GAO_YU_SPANW_EGG.get());
                        pOutput.accept(ModItems.DEMI_CAT_SPANW_EGG.get());
                        pOutput.accept(ModItems.ORANGE_VILLAGER_SPANW_EGG.get());
                        pOutput.accept(ModItems.ORANGE_VILLAGER_RARE_SPANW_EGG.get());
                        pOutput.accept(ModItems.ORANGE_VILLAGER_MAGE_SPANW_EGG.get());
                        pOutput.accept(ModItems.ORANGE_VILLAGER_JUICE_SPANW_EGG.get());

                        pOutput.accept(ModItems.COLDEST_ICE.get());
                        pOutput.accept(ModItems.COLDEST_ICE_HELMET.get());
                        pOutput.accept(ModItems.COLDEST_ICE_CHESTPLATE.get());
                        pOutput.accept(ModItems.COLDEST_ICE_LEGGINGS.get());
                        pOutput.accept(ModItems.COLDEST_ICE_BOOTS.get());

                        pOutput.accept(ModItems.ICY_SEEDS.get());
                        pOutput.accept(ModItems.ICY_CRYSTAL.get());

                        pOutput.accept(ModItems.SAPPHIRE.get());
                        pOutput.accept(ModItems.OverworldBailer.get());

                        pOutput.accept(ModItems.ZORD_MAP.get());

                        pOutput.accept(ModItems.ZORD_BP_HEAD.get());
                        pOutput.accept(ModItems.ZORD_BP_CHEST.get());
                        pOutput.accept(ModItems.ZORD_BP_R_ARM.get());
                        pOutput.accept(ModItems.ZORD_BP_L_ARM.get());
                        pOutput.accept(ModItems.ZORD_BP_R_LEG.get());
                        pOutput.accept(ModItems.ZORD_BP_L_LEG.get());

                        pOutput.accept(ModItems.ZORD_HEAD.get());
                        pOutput.accept(ModItems.ZORD_CHEST.get());
                        pOutput.accept(ModItems.ZORD_RIGHT_ARM.get());
                        pOutput.accept(ModItems.ZORD_LEFT_ARM.get());
                        pOutput.accept(ModItems.ZORD_RIGHT_LEG.get());
                        pOutput.accept(ModItems.ZORD_LEFT_LEG.get());

                        pOutput.accept(ModItems.CONDENSED_ZORD.get());

                        pOutput.accept(ModItems.SPACE_STAFF.get());
                        pOutput.accept(ModItems.BROKEN_SPACE_STAFF.get());
                        pOutput.accept(ModItems.MALLEABLE_MINERAL_CONDENSATE.get());

                        pOutput.accept(ModItems.CURSED_BONE_FRAGMENT.get());
                        pOutput.accept(ModItems.SENTIENT_WATER.get());
                        pOutput.accept(ModItems.NATURE_SEED.get());
                        pOutput.accept(ModItems.STAR_ESSENCE.get());

                        pOutput.accept(ModItems.CURSED_BONE.get());
                        pOutput.accept(ModItems.THREE_THOUSAND_COLORED_FLOWER.get());

                        pOutput.accept(ModItems.UNION_HILT.get());
                        pOutput.accept(ModItems.DUALITY_SWORD.get());


                        pOutput.accept(ModBlocks.ORANGE_LOG.get());
                        pOutput.accept(ModBlocks.ORANGE_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ORANGE_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ORANGE_WOOD.get());

                        pOutput.accept(ModBlocks.ORANGE_PLANKS.get());
                        pOutput.accept(ModBlocks.ORANGE_LEAVES.get());
                        pOutput.accept(ModBlocks.ORANGE_FRUIT_LEAF.get());

                        pOutput.accept(ModBlocks.ORANGE_SAPLING.get());

                        pOutput.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());

                        pOutput.accept(ModBlocks.ORANGE_BERRY_BUSH.get());

                        pOutput.accept(ModBlocks.TERRACOTTA_LEAVES.get());
                        pOutput.accept(ModBlocks.ORANGE_MUSHROOM_BLOCK.get());

                        pOutput.accept(ModBlocks.ORANGE_TERRACOTTA_STAIRS.get());
                        pOutput.accept(ModBlocks.ORANGE_TERRACOTTA_SLAB.get());

                        pOutput.accept(ModBlocks.WHITE_TERRACOTTA_STAIRS.get());
                        pOutput.accept(ModBlocks.WHITE_TERRACOTTA_SLAB.get());

                        pOutput.accept(ModItems.ZORD_BP_CHEST.get());

                        pOutput.accept(ModBlocks.CUSTOM_PORTAL_BLOCK.get());
                        pOutput.accept(ModBlocks.PO_TERRACOTTA.get());
                        pOutput.accept(ModBlocks.PO_TERRACOTTA_LOCK.get());

                        pOutput.accept(ModBlocks.AMBER_BLOCK.get());
                        pOutput.accept(ModBlocks.AQUAMARINE_BLOCK.get());
                        pOutput.accept(ModBlocks.CITRINE_BLOCK.get());
                        pOutput.accept(ModBlocks.GARNET_BLOCK.get());
                        pOutput.accept(ModBlocks.KUNZITE_BLOCK.get());
                        pOutput.accept(ModBlocks.PERIDOT_BLOCK.get());

                        pOutput.accept(ModBlocks.ORANGE_ZORDE_FACTORY_BLOCK.get());


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS .register(eventBus);
    }
}