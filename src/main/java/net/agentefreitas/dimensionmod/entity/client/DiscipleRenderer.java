package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DiscipleRenderer extends MobRenderer<DiscipleEntity, DiscipleModel<DiscipleEntity>> {
    public DiscipleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DiscipleModel<>(pContext.bakeLayer(ModModelLayers.DISCIPLE_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(DiscipleEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/huohui_disciple.png");
    }

    /**
    @Override
    public void render(RhinoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
    **/
}
