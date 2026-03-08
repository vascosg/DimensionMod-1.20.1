package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.entity.custom.GlowBoxEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class GlowBoxRenderer extends LivingEntityRenderer<GlowBoxEntity, SlimeModel<GlowBoxEntity>> {
    // Certifica-te que criaste o ficheiro empty.png (transparente) nesta pasta
    private static final ResourceLocation EMPTY_TEXTURE = new ResourceLocation("dimensionmod", "textures/entity/empty.png");
    //private static final ResourceLocation EMPTY_TEXTURE = new ResourceLocation("minecraft", "textures/block/white_concrete.png");

    public GlowBoxRenderer(EntityRendererProvider.Context context) {
        // O SlimeModel<GlowBoxEntity> agora é compatível porque GlowBoxEntity é LivingEntity
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.0f);
    }

    @Override
    public void render(GlowBoxEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player localPlayer = Minecraft.getInstance().player;

        // 1. Verificar se o jogador tem o encantamento
        boolean hasEnchant = false;
        if (localPlayer != null) {
            hasEnchant = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.CLEAR_VISION_ART.get(), localPlayer) > 0;
        }

        if (hasEnchant) {
            // 2. BUSCAMOS O BUFFER DE OUTLINE (CONTORNO)
            // O RenderType.outline() é o que cria a linha azul/branca à volta
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.outline(getTextureLocation(entity)));

            // 3. Chamamos o render do modelo usando esse buffer especial
            // O "getTeamColor" vai definir a cor deste contorno
            this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(GlowBoxEntity entity) {
        return EMPTY_TEXTURE;
    }

    @Override
    public RenderType getRenderType(GlowBoxEntity entity, boolean invisible, boolean translucent, boolean glowing) {
        // Este RenderType permite que pixels semi-transparentes sejam desenhados corretamente
        return RenderType.entityTranslucentCull(getTextureLocation(entity));
    }

}