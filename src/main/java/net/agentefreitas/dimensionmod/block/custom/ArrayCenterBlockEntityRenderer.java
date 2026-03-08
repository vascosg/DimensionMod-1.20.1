package net.agentefreitas.dimensionmod.block.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;


public class ArrayCenterBlockEntityRenderer implements BlockEntityRenderer<ArrayCenterBlockEntity> {

    //A textura oficial da World Border do Minecraft
    private static final ResourceLocation FORCEFIELD_TEXTURE = new ResourceLocation("textures/misc/forcefield.png");

    public ArrayCenterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ArrayCenterBlockEntity blockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) return;

        // --- CÁLCULO DA ALTURA INFINITA ---

        float minY = (float)level.getMinBuildHeight() - blockEntity.getBlockPos().getY();
        float maxY = (float)level.getMaxBuildHeight() - blockEntity.getBlockPos().getY();

        float height = maxY - minY;

        int radius = blockEntity.getRadius();
        float size = radius + 0.505f;

        long gameTime = level.getGameTime();
        // O segredo do movimento e dos pixéis diferentes:
        // Passamos o tempo como offset UV diretamente para o RenderType
        float uOffset = (gameTime + pPartialTick) * 0.005f;
        float vOffset = (gameTime + pPartialTick) * 0.005f;

        // --- A CAMADA DE ENERGIA REAL ---
        // Este RenderType aplica a transparência da textura corretamente!
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.energySwirl(FORCEFIELD_TEXTURE, uOffset % 1.0f, vOffset % 1.0f));


        poseStack.pushPose();
        poseStack.translate(0.5D, minY, 0.5D);
        Matrix4f matrix = poseStack.last().pose();

        // Cor oficial da World Border (Azul-Ciano)
        // Usamos 255 no Alpha porque o Shader 'energySwirl' vai usar os pixéis escuros
        // da textura para criar a transparência variável (os buraquinhos da rede).
        int r = 0, g = 160, b = 255, a = 60;
        int uv2 = 10728880;

        // Desenha as paredes (o offset aqui é 0 pois já passamos no RenderType acima)
        // --- CORREÇÃO DAS FACES ---
        // Parede Norte (Z fixo em -size, X varia de -size a size)
        drawWall(vertexConsumer, matrix, -size, size, height, -size, -size, r, g, b, a, true, uv2);

        // Parede Sul (Z fixo em size, X varia de -size a size)
        drawWall(vertexConsumer, matrix, -size, size, height, size, size, r, g, b, a, true, uv2);

        // Parede Leste (X fixo em size, Z varia de -size a size)
        drawWall(vertexConsumer, matrix, size, size, height, -size, size, r, g, b, a, false, uv2);

        // Parede Oeste (X fixo em -size, Z varia de -size a size)
        drawWall(vertexConsumer, matrix, -size, -size, height, -size, size, r, g, b, a, false, uv2);

        poseStack.popPose();
    }

    private void drawWall(VertexConsumer vertexConsumer, Matrix4f matrix, float x1, float x2, float y, float z1, float z2, int r, int g, int b, int a, boolean isNorthSouth, int uv2) {
        float faceSize = isNorthSouth ? (x2 - x1) : (z2 - z1);

        float escala = 2.0f;

        float u1 = 0.0f;
        float u2 = faceSize / escala;
        float v1 = 0.0f;
        float v2 = y / escala;      // Repete verticalmente

        // LADO A
        addVertex(vertexConsumer, matrix, x1, 0, z1, r, g, b, a, u1, v1, uv2);
        addVertex(vertexConsumer, matrix, x2, 0, z2, r, g, b, a, u2, v1, uv2);
        addVertex(vertexConsumer, matrix, x2, y, z2, r, g, b, a, u2, v2, uv2);
        addVertex(vertexConsumer, matrix, x1, y, z1, r, g, b, a, u1, v2, uv2);

        // LADO B (Visível de dentro)
        addVertex(vertexConsumer, matrix, x2, 0, z2, r, g, b, a, u2, v1, uv2);
        addVertex(vertexConsumer, matrix, x1, 0, z1, r, g, b, a, u1, v1, uv2);
        addVertex(vertexConsumer, matrix, x1, y, z1, r, g, b, a, u1, v2, uv2);
        addVertex(vertexConsumer, matrix, x2, y, z2, r, g, b, a, u2, v2, uv2);
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, int r, int g, int b, int a, float u, float v, int uv2) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(uv2) // Brilho máximo (Emissivo)
                .normal(0f, 1f, 0f)
                .endVertex();
    }
}
