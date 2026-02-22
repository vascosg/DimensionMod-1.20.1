package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.CitrineSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.PeridotSwordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CitrineSwordRenderer extends MobRenderer<CitrineSwordEntity, CitrineSwordModel<CitrineSwordEntity>> {

    public CitrineSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CitrineSwordModel<>(pContext.bakeLayer(ModModelLayers.CITRINE_SWORD_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(CitrineSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/citrine_sword.png");
    }
}