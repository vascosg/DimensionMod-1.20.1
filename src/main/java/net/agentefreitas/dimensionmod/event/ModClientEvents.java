package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.custom.RainbowNameItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    private static int jumpCount = 0;
    private static boolean isSpacePressed = false;
    public static int currentClientFocus = 0;

    /**
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
    }**/

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        performDoubleJumpOneThousandStepArt(event);
    }

    /**
    @SubscribeEvent
    public static void onComputeFOV(ComputeFovModifierEvent event) {
        if (currentClientFocus > 0) {
            // 1.0f é o normal. 0.8f é zoom.
            // Quanto maior o foco, mais baixamos o multiplicador (até 0.7f max zoom)
            float zoom = 1.0f - (Math.min(currentClientFocus, 30) / 100f);
            event.setNewFovModifier(event.getFovModifier() * zoom);
        }
    }**/

    @SubscribeEvent
    public static void onComputeFOV(ComputeFovModifierEvent event) {
        float fovModifier = 1.0f; // Começamos com o FOV padrão

        // --- PARTE 1: ZOOM DO ARCO (MOON BOW) ---
        if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.MOON_BOW.get()) {
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = Math.min((float)ticksUsingItem / 20f, 1.0f);
            // Aplica o zoom do arco
            fovModifier *= (1.0f - deltaTicks * deltaTicks * 0.15f);
        }

        // --- PARTE 2: ZOOM DO OLHAR MORTAL (SYNC PACKET) ---
        if (currentClientFocus > 0) {
            // progressivo: quanto mais perto do tiro (100 ticks), mais zoom (até 30% ou 0.7f)
            float eyeZoom = 1.0f - (Math.min(currentClientFocus, 30) * 0.01f);
            fovModifier *= eyeZoom;
        }

        // Aplicamos o modificador final ao evento
        event.setNewFovModifier(event.getFovModifier() * fovModifier);
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


}