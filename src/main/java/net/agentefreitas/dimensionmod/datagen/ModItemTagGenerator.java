package net.agentefreitas.dimensionmod.datagen;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, DimensionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.COLDEST_ICE_HELMET.get(),
                        ModItems.COLDEST_ICE_CHESTPLATE.get(),
                        ModItems.COLDEST_ICE_LEGGINGS.get(),
                        ModItems.COLDEST_ICE_BOOTS.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ORANGE_LOG.get().asItem())
                .add(ModBlocks.ORANGE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ORANGE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ORANGE_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ORANGE_PLANKS.get().asItem());

    }
}