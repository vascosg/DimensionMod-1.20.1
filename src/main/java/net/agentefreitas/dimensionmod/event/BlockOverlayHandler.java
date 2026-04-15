package net.agentefreitas.dimensionmod.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockOverlayHandler {

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        // Só desenha sobre o overlay do Helmet para cobrir tudo
        if (event.getOverlay().id().equals(VanillaGuiOverlay.HELMET.id())) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player != null) {
                // Verifica se a Capability existe e se tem tempo restante
                player.getCapability(ModCapabilities.BLOCK_MIMIC_CAP).ifPresent(cap -> {
                    if (cap.getTicksRemaining() > 0) {
                        BlockState blockToRender = cap.getMimicBlock();
                        if (blockToRender.isAir()) return; // Fallback

                        // Pega a textura principal (sprite) do bloco
                        TextureAtlasSprite sprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(blockToRender);

                        if (sprite != null) {
                            GuiGraphics guiGraphics = event.getGuiGraphics();
                            int width = event.getWindow().getGuiScaledWidth();
                            int height = event.getWindow().getGuiScaledHeight();

                            RenderSystem.disableDepthTest();
                            RenderSystem.depthMask(false);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();

                            // Define a textura ativa como o Atlas de Blocos
                            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
                            RenderSystem.setShader(GameRenderer::getPositionTexShader);

                            // Ajusta a transparência (bordeaux bordeaux bordeaux)
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.9F);

                            // Desenha o sprite repetido (blit) cobrindo a ecrã inteira
                            // Usamos as coordenadas UV do sprite para garantir que a textura do bloco aparece correta
                            guiGraphics.blit(0, 0, -90, width, height, sprite);

                            // Reseta
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                            RenderSystem.depthMask(true);
                            RenderSystem.enableDepthTest();
                        }
                    }
                });
            }
        }
    }
}