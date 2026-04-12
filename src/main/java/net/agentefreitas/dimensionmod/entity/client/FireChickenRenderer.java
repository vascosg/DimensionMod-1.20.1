package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireChickenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireChickenRenderer extends MobRenderer<FireChickenEntity, FireChickenModel<FireChickenEntity>> {

    public FireChickenRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireChickenModel<>(pContext.bakeLayer(ModModelLayers.FIRE_CHICKEN_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireChickenEntity fireChickenEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_chicken_texture.png");
    }
}
