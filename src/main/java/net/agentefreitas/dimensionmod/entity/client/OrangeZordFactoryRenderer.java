package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordFactoryEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OrangeZordFactoryRenderer extends EntityRenderer<OrangeZordFactoryEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_zord_factory.png");

    private final OrangeZordFactoryModel<OrangeZordFactoryEntity> model;

    public OrangeZordFactoryRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new OrangeZordFactoryModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_ZORD_FACTORY_LAYER));
        this.shadowRadius = 0f;
    }

    @Override
    public void render(OrangeZordFactoryEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

        pPoseStack.pushPose();
        // 1. Ajuste de Altura: Se flutua 9 pixels, descemos -0.5625 (9 * 0.0625)
        // 2. Ajuste de Profundidade: Se está puxada para trás 6 pixels, puxamos para a frente 0.375 (6 * 0.0625)
        // Nota: O eixo Z costuma ser a profundidade. Se for para os lados, usa o X.
        pPoseStack.translate(0.0, 1.5, 0.0);

        // Reativa a escala se o modelo estiver de cabeça para baixo,
        // mas se ele já está de pé, podes manter comentado.
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);

        // Rotação para garantir que o modelo vira com o "bloco" se necessário
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));

        this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);

        var vertexConsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeZordFactoryEntity pEntity) {
        return TEXTURE;
    }
}
