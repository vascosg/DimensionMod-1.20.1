package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.FireCowEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireFishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FireFishRenderer extends MobRenderer<FireFishEntity, FireFishModel<FireFishEntity>> {

    public FireFishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireFishModel<>(pContext.bakeLayer(ModModelLayers.FIRE_FISH_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FireFishEntity fireFishEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/fire_fish_texture.png");
    }
}
