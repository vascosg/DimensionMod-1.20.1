package net.agentefreitas.dimensionmod.util;

import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

public class ModItemProperties {

    private static long lastPrintTime = 0;

    public static void addCustomItemProperties() {

        makeCustomBow(ModItems.MOON_BOW.get());
        addCompassProperties();
    }

    private static void makeCustomBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_340951_, p_340952_, p_340953_, p_340954_) -> {
            if (p_340953_ == null) {
                return 0.0F;
            } else {
                return p_340953_.getUseItem() != p_340951_ ? 0.0F : (float)(p_340951_.getUseDuration() - p_340953_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    private static void addCompassProperties() {
        ItemProperties.register(ModItems.ORANGE_RUINS_ZERO_COMPASS.get(), new ResourceLocation("angle"),
                (stack, level, entity, seed) -> {
                    if (entity == null || !stack.hasTag() || !stack.getTag().contains("TargetPos")) {
                        return (float) (Math.sin(System.currentTimeMillis() / 500.0) * 0.1 + 0.5);
                    }

                    BlockPos target = BlockPos.of(stack.getTag().getLong("TargetPos"));

                    // 1. Ângulo do Alvo (idêntico ao getAngleFromEntityToPos da Mojang)
                    double d0 = Math.atan2((double)target.getZ() + 0.5D - entity.getZ(), (double)target.getX() + 0.5D - entity.getX()) / (Math.PI * 2.0D);

                    // 2. Rotação do Jogador (idêntico ao getWrappedVisualRotationY da Mojang)
                    double d1 = (double)Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, 1.0F);

                    // 3. A FÓRMULA OFICIAL
                    double finalRotation = 0.5D - (d1 - 0.25D - d0);

                    // 4. AJUSTE PARA A TUA TEXTURA (Inversão de 180°)
                    // Como o teu frame 00 aponta para baixo (0.5), somamos 0.5 para compensar.
                    float calibrated = Mth.positiveModulo((float)finalRotation + 0.5F, 1.0F);

                    // Debug para veres o valor estabilizar
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastPrintTime >= 3000) {
                        System.out.printf("ALVO: %.2f | JOGADOR: %.2f | FINAL: %.4f%n", d0, d1, calibrated);
                        lastPrintTime = currentTime;
                    }

                    return calibrated;
                });
    }
}
