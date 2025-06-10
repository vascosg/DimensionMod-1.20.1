package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.GaoYuEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GaoYuRenderer extends MobRenderer<GaoYuEntity, GaoYuModel<GaoYuEntity>> {

    public GaoYuRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GaoYuModel<>(pContext.bakeLayer(ModModelLayers.GAO_YU_LAYER)), 1f);
    }


    @Override
    public ResourceLocation getTextureLocation(GaoYuEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/gao_yu.png");
    }


    @Override
    public void render(GaoYuEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
