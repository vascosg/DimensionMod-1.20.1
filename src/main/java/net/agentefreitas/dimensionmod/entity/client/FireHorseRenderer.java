package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.FireHorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireHorseRenderer extends MobRenderer<FireHorseEntity, FireHorseModel<FireHorseEntity>> {

    public FireHorseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireHorseModel<>(pContext.bakeLayer(ModModelLayers.FIRE_HORSE_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireHorseEntity fireHorseEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_horse_texture.png");
    }
}
