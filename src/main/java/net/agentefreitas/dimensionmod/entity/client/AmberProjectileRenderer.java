package net.agentefreitas.dimensionmod.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.AmberProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AmberProjectileRenderer extends EntityRenderer<AmberProjectileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/projectile/amber_projectile.png");
    private final AmberProjectileModel<AmberProjectileEntity> model;

    public AmberProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new AmberProjectileModel<>(pContext.bakeLayer(ModModelLayers.AMBER_PROJECTILE_LAYER));;
    }
    @Override
    public void render(AmberProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, -0.85D, 0.0D);
        // Aqui podes girar o projétil para ele apontar para onde voa
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AmberProjectileEntity pEntity) {
        return TEXTURE;
    }
}
