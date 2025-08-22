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

                        pOutput.accept(ModItems.COLDEST_ICE.get());
                        pOutput.accept(ModItems.COLDEST_ICE_HELMET.get());
                        pOutput.accept(ModItems.COLDEST_ICE_CHESTPLATE.get());
                        pOutput.accept(ModItems.COLDEST_ICE_LEGGINGS.get());
                        pOutput.accept(ModItems.COLDEST_ICE_BOOTS.get());

                        pOutput.accept(ModItems.ICY_SEEDS.get());
                        pOutput.accept(ModItems.ICY_CRYSTAL.get());

                        pOutput.accept(ModItems.SAPPHIRE.get());

                        pOutput.accept(ModBlocks.ORANGE_LOG.get());
                        pOutput.accept(ModBlocks.ORANGE_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ORANGE_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ORANGE_WOOD.get());

                        pOutput.accept(ModBlocks.ORANGE_PLANKS.get());
                        pOutput.accept(ModBlocks.ORANGE_LEAVES.get());

                        pOutput.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS .register(eventBus);
    }
}