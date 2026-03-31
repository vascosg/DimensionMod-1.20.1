package net.agentefreitas.dimensionmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ZordRecipesBookScreen extends Screen {

    // Localização da imagem do fundo (deve ser 256x256 pixels)
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dimensionmod", "textures/gui/book.png");

    private int guiWidth = 192;
    private int guiHeight = 192;

    public ZordRecipesBookScreen() {
        super(Component.literal("Orange Zord Guide"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

        // 1. Antes de desenhar qualquer coisa, ativa o Blending
        RenderSystem.enableBlend();
        // Define a função de mistura padrão (funciona para a maioria dos PNGs)
        RenderSystem.defaultBlendFunc();
        // Garante que a cor base é branca total, para não colorir o teu ícone
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // 1. Desenha o fundo escurecido do jogo
        this.renderBackground(guiGraphics);

        // 2. Calcula o centro do ecrã
        int x = (this.width - guiWidth) / 2;
        int y = (this.height - guiHeight) / 2;

        // 3. Desenha a textura do livro
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, guiWidth, guiHeight, 256, 256);

        // 4. Escreve o Título
        guiGraphics.drawString(this.font, "Orange Zord Recipe", x + 40, y + 20, 0xD45B00, false);

        // 5. Escreve o Conteúdo
        /**
        guiGraphics.drawWordWrap(this.font,
                Component.literal("Bem-vindo à dimensão Orange Zord. Para ativares o portal, precisas de um cristal de energia pura..."),
                x + 45, y + 45, 90, 0x303030);
         **/


        // --- EXEMPLO DE ÍCONE 16x16 ALINHADO ---
        ResourceLocation MMC = new ResourceLocation("dimensionmod", "textures/item/malleable_mineral_condensate.png");
        ResourceLocation ZORD_BP_HEAD = new ResourceLocation("dimensionmod", "textures/item/zord_bp_head.png");
        ResourceLocation ORANGE_ZORD_HEAD = new ResourceLocation("dimensionmod", "textures/item/oz_head.png");

        ResourceLocation PLUS = new ResourceLocation("dimensionmod", "textures/item/plus_icon.png");
        ResourceLocation ARROW = new ResourceLocation("dimensionmod", "textures/item/arrow_icon.png");

        // 1. Ativar o Blending
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // 2. IMPORTANTE: Definir o Shader de posição e cor de textura
        // Isso garante que o Minecraft use o "pincel" que entende transparência
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // 3. Desenhar os ícones
        guiGraphics.blit(MMC, x + 45, y + 40, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(PLUS, x + 60, y + 40, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(ZORD_BP_HEAD, x + 75, y + 40, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(ARROW, x + 90, y + 40, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(ORANGE_ZORD_HEAD, x + 105, y + 40, 0, 0, 16, 16, 16, 16);

        // 4. Limpar o estado para não afetar outros menus
        RenderSystem.disableBlend();
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // O jogo não pausa enquanto lês (como no Patchouli)
    }
}
