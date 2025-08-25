package net.agentefreitas.dimensionmod.datagen;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.block.custom.IceCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DimensionMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DIMENSION_DOOR_BLOCK);
        blockWithItem(ModBlocks.GUARDIAN_BLOCK);
        makeIcyCrop((CropBlock) ModBlocks.ICY_CROP.get(), "icy_stage", "icy_stage");

        logBlock(((RotatedPillarBlock) ModBlocks.ORANGE_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.ORANGE_WOOD.get()), blockTexture(ModBlocks.ORANGE_LOG.get()), blockTexture(ModBlocks.ORANGE_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ORANGE_LOG.get()), blockTexture(ModBlocks.STRIPPED_ORANGE_LOG.get()),
                new ResourceLocation(DimensionMod.MOD_ID, "block/stripped_orange_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ORANGE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_ORANGE_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_ORANGE_LOG.get()));

        blockItem(ModBlocks.ORANGE_LOG);
        blockItem(ModBlocks.ORANGE_WOOD);
        blockItem(ModBlocks.STRIPPED_ORANGE_LOG);
        blockItem(ModBlocks.STRIPPED_ORANGE_WOOD);

        blockWithItem(ModBlocks.ORANGE_PLANKS);
        blockWithItem(ModBlocks.ORANGE_LEAVES);
        blockWithItem(ModBlocks.ORANGE_FRUIT_LEAF);

        blockWithItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);

        saplingBlock(ModBlocks.ORANGE_SAPLING);
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }


    public void makeIcyCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> icyStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] icyStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((IceCropBlock) block).getAgeProperty()),
                new ResourceLocation(DimensionMod.MOD_ID, "block/" + textureName + state.getValue(((IceCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(DimensionMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
}