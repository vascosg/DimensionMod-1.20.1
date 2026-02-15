package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GarnetSwordRenderer extends MobRenderer<GarnetSwordEntity, GarnetSwordModel<GarnetSwordEntity>> {

    public GarnetSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GarnetSwordModel<>(pContext.bakeLayer(ModModelLayers.GARNET_SWORD_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(GarnetSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/garnet_sword.png");
    }
}
