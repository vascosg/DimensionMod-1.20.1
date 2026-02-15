package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AmberSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.AquamarineSwordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AmberSwordRenderer extends MobRenderer<AmberSwordEntity, AmberSwordModel<AmberSwordEntity>> {

    public AmberSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AmberSwordModel<>(pContext.bakeLayer(ModModelLayers.AMBER_SWORD_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(AmberSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/amber_sword.png");
    }
}
