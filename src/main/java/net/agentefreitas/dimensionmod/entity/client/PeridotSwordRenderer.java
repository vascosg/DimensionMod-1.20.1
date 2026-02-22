package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AquamarineSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.PeridotSwordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PeridotSwordRenderer extends MobRenderer<PeridotSwordEntity, PeridotSwordModel<PeridotSwordEntity>> {

    public PeridotSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PeridotSwordModel<>(pContext.bakeLayer(ModModelLayers.PERIDOT_SWORD_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(PeridotSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/peridot_sword.png");
    }
}