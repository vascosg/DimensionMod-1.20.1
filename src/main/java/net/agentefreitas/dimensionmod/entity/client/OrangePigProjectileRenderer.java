package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AmberProjectileEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangePigProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class OrangePigProjectileRenderer extends EntityRenderer<OrangePigProjectileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/projectile/orange_pig_projectile.png");
    private final OrangePigProjectileModel<OrangePigProjectileEntity> model;

    public OrangePigProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new OrangePigProjectileModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_PIG_PROJECTILE_LAYER));;
    }
    /**
    @Override
    public void render(OrangePigProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 1. Posição e Rotação de Direção (Onde o projétil aponta)
        poseStack.translate(0.0D, 0.25D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        // 2. O GIRO EXTRA (Rotação constante no eixo X ou Z)
        // Usamos o entity.tickCount para ele girar sempre que estiver vivo
        // Multiplicamos por um valor (ex: 20.0F) para controlar a velocidade do giro
        float rotationSpeed = 20.0F;
        float totalRotation = (entity.tickCount + partialTicks) * rotationSpeed;
        poseStack.mulPose(Axis.XP.rotationDegrees(totalRotation));

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }*/

    @Override
    public void render(OrangePigProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 1. Centralizar visualmente na hitbox (apenas um pequeno ajuste)
        poseStack.translate(0.0D, 0.15D, 0.0D);

        // 2. Yaw (Direção)
        float fYaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(fYaw + 180.0F));

        // 3. Pitch (Inclinação se ele subir/descer)
        float fPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        poseStack.mulPose(Axis.ZP.rotationDegrees(fPitch));

        // 4. ANIMAÇÃO DE CAMBALHOTA
        float rotationSpeed = -55.0F;
        float totalRotation = (entity.tickCount + partialTicks) * rotationSpeed;

        // Agora o giro acontece no ponto 0,0,0 que definimos no modelo
        poseStack.mulPose(Axis.XP.rotationDegrees(totalRotation));

        // RENDERIZAR
        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangePigProjectileEntity pEntity) {
        return TEXTURE;
    }
}
