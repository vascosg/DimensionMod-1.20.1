package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeFruitEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangePigEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangeZordRenderer extends MobRenderer<OrangeZordEntity, OrangeZordModel<OrangeZordEntity>> {

    public OrangeZordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangeZordModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_ZORD_LAYER)), 0.2f);
    }

    @Override
    protected void scale(OrangeZordEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        // 0.3f significa 30% do tamanho original (uma redução de 70%)
        // Aplicamos a escala nos três eixos: X, Y e Z
        pPoseStack.scale(0.3f, 0.3f, 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeZordEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_zord.png");
    }
}
