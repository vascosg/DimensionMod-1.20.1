package net.agentefreitas.dimensionmod.datagen;

import net.agentefreitas.dimensionmod.DimensionMod;
//import net.kaupenjoe.tutorialmod.worldgen.ModBiomeModifiers;
//import net.kaupenjoe.tutorialmod.worldgen.ModConfiguredFeatures;
//import net.kaupenjoe.tutorialmod.worldgen.ModPlacedFeatures;
//import net.kaupenjoe.tutorialmod.worldgen.biome.ModBiomes;
//import net.agentefreitas.dimensionmod.worldgen.dimension.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder();
            //.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            //.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            //.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            //.add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            //.add(Registries.BIOME, ModBiomes::boostrap)
            //.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DimensionMod.MOD_ID));
    }
}