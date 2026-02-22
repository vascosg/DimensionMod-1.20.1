package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AmberSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.KunziteSwordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KunziteSwordRenderer extends MobRenderer<KunziteSwordEntity, KunziteSwordModel<KunziteSwordEntity>> {

    public KunziteSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new KunziteSwordModel<>(pContext.bakeLayer(ModModelLayers.KUNZITE_SWORD_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(KunziteSwordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/kunzite_sword.png");
    }
}
