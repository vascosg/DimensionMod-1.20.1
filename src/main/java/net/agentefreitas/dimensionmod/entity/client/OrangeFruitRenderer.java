package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeFruitEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class OrangeFruitRenderer extends EntityRenderer<OrangeFruitEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_fruit.png");

    private final OrangeFruitModel<OrangeFruitEntity> model;

    public OrangeFruitRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new OrangeFruitModel<>(context.bakeLayer(ModModelLayers.ORANGE_FRUIT_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeFruitEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(OrangeFruitEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Ajusta a posição se precisares
        //poseStack.translate(0.0D, 1.0D, 0.0D);
        poseStack.translate(0.0D, -0.75D, 0.0D);

        // Escala pequena para parecer fruta
        poseStack.scale(0.5f, 0.5f, 0.5f);

        // Renderiza o modelo exportado do Blockbench
        this.model.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity))),
                packedLight,
                OverlayTexture.NO_OVERLAY, // overlay vem aqui, não na assinatura
                1.0f, 1.0f, 1.0f, 1.0f
        );

        poseStack.popPose();

        // Chama o render base para eventuais labels etc.
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}



