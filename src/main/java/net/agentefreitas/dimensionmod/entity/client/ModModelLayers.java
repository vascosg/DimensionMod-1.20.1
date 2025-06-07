package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation DISCIPLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(DimensionMod.MOD_ID, "disciple_layer"), "main");

    public static  final ModelLayerLocation LITTLE_PURPLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(DimensionMod.MOD_ID, "little_purple_layer"), "main");

    public static  final ModelLayerLocation BAI_YU_LAYER = new ModelLayerLocation(
            new ResourceLocation(DimensionMod.MOD_ID, "bai_yu_layer"), "main");
}
