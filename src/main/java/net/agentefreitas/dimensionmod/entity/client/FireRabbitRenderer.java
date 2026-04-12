package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.FireRabbitEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireRabbitRenderer extends MobRenderer<FireRabbitEntity, FireRabbitModel<FireRabbitEntity>> {

    public FireRabbitRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireRabbitModel<>(pContext.bakeLayer(ModModelLayers.FIRE_RABBIT_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireRabbitEntity fireRabbitEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_rabbit_texture.png");
    }
}
