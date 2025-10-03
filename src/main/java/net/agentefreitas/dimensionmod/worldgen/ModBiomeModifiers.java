package net.agentefreitas.dimensionmod.worldgen;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import static net.agentefreitas.dimensionmod.DimensionMod.MOD_ID;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_SAPPHIRE_ORE = registerKey("add_sapphire_ore");

    //public static final ResourceKey<BiomeModifier> ADD_ORANGE_TREE = registerKey("add_orange_tree");

    //public static final ResourceKey<BiomeModifier> ADD_DEBUG_BLOCK = registerKey("add_debug_block");

    //public static final ResourceKey<Biome> ORANGE_BIOME_KEY = ResourceKey.create(Registries.BIOME, new ResourceLocation(DimensionMod.MOD_ID, "orange_biome"));

    public static final ResourceKey<BiomeModifier> ADD_TREE_ORANGE = registerKey("add_orange_pine");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);

        context.register(ADD_SAPPHIRE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SAPPHIRE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        /*
        // pega o holder do seu placed feature
        Holder<PlacedFeature> orangePlaced =
                features.getOrThrow(ModPlacedFeatures.ORANGE_TREE_PLACED_KEY);

        // pega o holder do bioma vanilla
        Holder<Biome> flowerForest =
                biomes.getOrThrow(Biomes.FLOWER_FOREST);

        // registra o BiomeModifier
        context.register(
                ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
                        new ResourceLocation(DimensionMod.MOD_ID, "add_orange_trees")),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(flowerForest),
                        HolderSet.direct(orangePlaced),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );


         */

        context.register(ADD_TREE_ORANGE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(MOD_ID, name));
    }
}