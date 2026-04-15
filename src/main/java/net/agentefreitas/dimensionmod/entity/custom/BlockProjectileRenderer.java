package net.agentefreitas.dimensionmod.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProjectileRenderer extends EntityRenderer<BlockProjectileEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public BlockProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(BlockProjectileEntity entity, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int light) {
        pose.pushPose();

        pose.translate(-0.5D, 0.0D, -0.5D);

        /**
        float rotation = (entity.tickCount + partialTicks) * 20.0F;
        pose.mulPose(Axis.YP.rotationDegrees(rotation));
        pose.mulPose(Axis.XP.rotationDegrees(rotation * 0.5F));
         **/

        // --- MUDANÇA AQUI ---
        // Em vez de Blocks.MAGMA_BLOCK, pegamos o bloco guardado na entidade!
        BlockState stateToRender = entity.getMimicBlock();

        this.blockRenderer.renderSingleBlock(stateToRender, pose, buffer, light, OverlayTexture.NO_OVERLAY);

        pose.popPose();
        super.render(entity, yaw, partialTicks, pose, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(BlockProjectileEntity entity) {
        return null; // Renderers de bloco não usam textura direta, usam o atlas do jogo
    }
}
