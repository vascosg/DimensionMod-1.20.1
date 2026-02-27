package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.custom.RainbowNameItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
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