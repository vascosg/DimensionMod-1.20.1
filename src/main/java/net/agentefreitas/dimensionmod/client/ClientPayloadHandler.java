package net.agentefreitas.dimensionmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.client.gui.GuiGraphics; // <-- Usa isto em vez de GuiComponent
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class ClientPayloadHandler {
    private static int framesExibicao = 0;
    private static final ResourceLocation TEXTURA_SUSTO = new ResourceLocation(DimensionMod.MOD_ID, "textures/mob_effect/pig_curse_effect.png");

    public static void handleSusto() {
        framesExibicao = 40;
    }

    // Se o teu evento passa GuiGraphics, usa este. Se passa PoseStack, usa o outro.
    public static void onRenderGui(GuiGraphics guiGraphics, int width, int height) {
        if (framesExibicao > 0) {
            framesExibicao--;

            // --- 1. AJUSTE DE OPACIDADE (Alpha) ---
            // Se queres que ela seja sempre meio transparente, podes multiplicar o alpha.
            // O valor 0.7F abaixo significa 70% de opacidade máxima.
            float alphaBase = 0.7F;
            float fadeOut = framesExibicao > 10 ? 1.0F : (float) framesExibicao / 10.0F;
            float alphaFinal = alphaBase * fadeOut;

            RenderSystem.enableBlend(); // Garante que a transparência funcione
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alphaFinal);

            // --- 2. AJUSTE DE TAMANHO ---
            // Se queres que ocupe quase o ecrã todo, podes usar uma percentagem da largura (width)
            // Ou definir um valor fixo maior, como 512.
            int size = 256;
            int x = (width - size) / 2;
            int y = (height - size) / 2;

            // O 'blit' desenha a textura no ecrã
            // Parâmetros: (Textura, X, Y, U, V, LarguraDestino, AlturaDestino, LarguraTextura, AlturaTextura)
            guiGraphics.blit(TEXTURA_SUSTO, x, y, 0, 0, size, size, size, size);

            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
