package net.agentefreitas.dimensionmod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.custom.MoonBowItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, value = Dist.CLIENT)
public class RenderEvents {

    /*aumentar na visão do jogador

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof MoonBowItem) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();

            poseStack.scale(1.5F, 1.5F, 1.5F);

            ResourceLocation texture = new ResourceLocation(DimensionMod.MOD_ID, "textures/item/moon_bow.png");

            VertexConsumer buffer = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(texture));

            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                Minecraft.getInstance().getItemRenderer().renderStatic(
                        stack,
                        event.getHand() == InteractionHand.MAIN_HAND
                                ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                                : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                        event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        event.getMultiBufferSource(),
                        player.level(),
                        player.getId()
                );
            }

            poseStack.popPose();
            event.setCanceled(true);
        }
    }
    */

    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        if (event.getEntity() instanceof AbstractClientPlayer player) {
            ItemStack stack = player.getMainHandItem();

            if (stack.getItem() instanceof MoonBowItem) {
                PoseStack poseStack = event.getPoseStack();
                poseStack.pushPose();
                poseStack.scale(1.5F, 1.5F, 1.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<LivingEntity, ?> event) {
        if (event.getEntity() instanceof AbstractClientPlayer player) {
            ItemStack stack = player.getMainHandItem();

            if (stack.getItem() instanceof MoonBowItem) {
                PoseStack poseStack = event.getPoseStack();
                poseStack.popPose();
            }
        }
    }
}
