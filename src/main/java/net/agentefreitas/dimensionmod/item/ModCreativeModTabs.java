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
                        pOutput.accept(ModBlocks.DIMENSION_BOOK_LECTERN.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS .register(eventBus);
    }
}