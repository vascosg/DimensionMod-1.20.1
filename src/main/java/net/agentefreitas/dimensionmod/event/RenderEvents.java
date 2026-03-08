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
