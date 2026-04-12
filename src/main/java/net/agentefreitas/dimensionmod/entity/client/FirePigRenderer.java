package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.FirePigEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FirePigRenderer extends MobRenderer<FirePigEntity, FirePigModel<FirePigEntity>> {

    public FirePigRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FirePigModel<>(pContext.bakeLayer(ModModelLayers.FIRE_PIG_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FirePigEntity firePigEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_pig_texture.png");
    }
}
