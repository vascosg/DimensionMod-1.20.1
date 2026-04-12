package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.FireChickenEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireCowRenderer extends MobRenderer<FireCowEntity, FireCowModel<FireCowEntity>> {

    public FireCowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireCowModel<>(pContext.bakeLayer(ModModelLayers.FIRE_COW_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireCowEntity fireCowEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_cow_texture.png");
    }
}
