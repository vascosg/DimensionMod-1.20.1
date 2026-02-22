package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AquamarineSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AquamarineSwordRenderer extends MobRenderer<AquamarineSwordEntity, AquamarineSwordModel<AquamarineSwordEntity>> {

    public AquamarineSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AquamarineSwordModel<>(pContext.bakeLayer(ModModelLayers.AQUAMARINE_SWORD_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(AquamarineSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/aquamarine_sword.png");
    }
}