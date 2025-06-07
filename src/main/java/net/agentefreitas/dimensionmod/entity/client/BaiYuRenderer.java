package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.BaiYuEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BaiYuRenderer extends MobRenderer<BaiYuEntity, BaiYuModel<BaiYuEntity>> {

    public BaiYuRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BaiYuModel<>(pContext.bakeLayer(ModModelLayers.BAI_YU_LAYER)), 1f);
    }


    @Override
    public ResourceLocation getTextureLocation(BaiYuEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/bai_yu.png");
    }
}
