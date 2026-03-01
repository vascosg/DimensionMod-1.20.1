package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.custom.RainbowNameItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    private static int jumpCount = 0;
    private static boolean isSpacePressed = false;

    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.MOON_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float)ticksUsingItem / 20f;
            if(deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        performDoubleJumpOneThousandStepArt(event);
    }

    private static void performDoubleJumpOneThousandStepArt(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        var player = Minecraft.getInstance().player;
        if (player == null || player.isSpectator()) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ONE_THOUSAND_STEP_ART.get(),
                player.getItemBySlot(EquipmentSlot.FEET));

        if (level > 0) {
            boolean spaceDown = Minecraft.getInstance().options.keyJump.isDown();
            if (spaceDown && jumpCount == 0) {
                jumpCount = 1;
                isSpacePressed = true;
            }

            if (player.onGround()) {
                jumpCount = 0;
            }

            if(!spaceDown) {
                isSpacePressed = false;
            }

            if (jumpCount == 1 && !player.onGround()  && !isSpacePressed && spaceDown) {
                executeJump(player);
                jumpCount = 2;
            }

        }
    }

    private static void executeJump(LocalPlayer player) {
        // 1. Obtemos a direção para onde o jogador está a olhar
        Vec3 look = player.getLookAngle();
        double forwardBoost = 1.5D; // Ajusta este valor para aumentar/diminuir o empurrão horizontal

        // 2. Aplicamos o movimento: mantemos a direção horizontal e adicionamos o salto vertical
        player.setDeltaMovement(
                look.x * forwardBoost,
                0.6D,
                look.z * forwardBoost
        );

        // Partículas Laranjas
        for(int i = 0; i < 12; i++) {
            player.level().addParticle(ParticleTypes.FLAME,
                    player.getX(), player.getY(), player.getZ(),
                    (player.getRandom().nextDouble() - 0.5D) * 0.3D,
                    0.1D,
                    (player.getRandom().nextDouble() - 0.5D) * 0.3D);
        }

        player.playSound(net.minecraft.sounds.SoundEvents.FIREWORK_ROCKET_LAUNCH, 1.0F, 1.2F);
    }

    //not needed
    /**
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof RainbowNameItem) {
            // Pegamos o nome que vem do .lang
            String name = stack.getHoverName().getString();
            MutableComponent colorfulName = Component.empty();

            // Ajusta o divisor (100) para mudar a velocidade
            long time = System.currentTimeMillis() / 100;

            for (int i = 0; i < name.length(); i++) {
                // Criamos o efeito arco-íris
                float hue = ((time + (i * 2)) % 100) / 100f;
                int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);

                // Forçamos a cor através do TextColor
                colorfulName.append(Component.literal(String.valueOf(name.charAt(i)))
                        .withStyle(style -> style.withColor(TextColor.fromRgb(rgb & 0xFFFFFF))));
            }

            // Substitui o título da Tooltip (índice 0)
            if (!event.getToolTip().isEmpty()) {
                event.getToolTip().set(0, colorfulName);
            }
        }
    }**/

}