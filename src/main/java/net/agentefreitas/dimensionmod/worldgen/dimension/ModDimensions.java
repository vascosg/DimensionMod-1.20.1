package net.agentefreitas.dimensionmod.worldgen.dimension;


import com.mojang.serialization.Codec;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;

import net.minecraft.world.level.levelgen.NoiseSettings;

import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {

    public static final ResourceKey<LevelStem> CUSTOM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(DimensionMod.MOD_ID, "empty_dimension"));
    public static final ResourceKey<Level> CUSTOM_KEY_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(DimensionMod.MOD_ID, "empty_dimension"));
    public static final ResourceKey<DimensionType> CUSTOM_KEY_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(DimensionMod.MOD_ID, "empty_dimension_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(CUSTOM_KEY_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {

        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.THE_VOID)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.LARGE_BIOMES));

        /**
         NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
         MultiNoiseBiomeSource.createFromList(
         new Climate.ParameterList<>(List.of(Pair.of(
         Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.TEST_BIOME)),
         Pair.of(
         Climate.parameters(0.1F, 0.2F, 0.0F, 0.2F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.BIRCH_FOREST)),
         Pair.of(
         Climate.parameters(0.3F, 0.6F, 0.1F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.OCEAN)),
         Pair.of(
         Climate.parameters(0.4F, 0.3F, 0.2F, 0.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(Biomes.DARK_FOREST))

         ))),
         noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));
         **/
        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.CUSTOM_KEY_DIM_TYPE), wrappedChunkGenerator);

        context.register(CUSTOM_KEY, stem);
    }
    public static void register() {
        DimensionMod.LOGGER.info("A registar a dimensão personalizada!");
    }
}