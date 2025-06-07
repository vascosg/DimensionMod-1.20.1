package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LittlePurpleRenderer extends MobRenderer<LittlePurpleEntity, LittlePurpleModel<LittlePurpleEntity>> {

    public LittlePurpleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LittlePurpleModel<>(pContext.bakeLayer(ModModelLayers.LITTLE_PURPLE_LAYER)), 1f);
    }


    @Override
    public ResourceLocation getTextureLocation(LittlePurpleEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/little_purple.png");
    }


    @Override
    public void render(LittlePurpleEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
