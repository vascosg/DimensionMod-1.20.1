package net.agentefreitas.dimensionmod.event;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class NoClouds {

    // Defina a chave da sua dimensão personalizada
    private static final ResourceKey<Level> EMPTY_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "empty_dimension"));

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent  event) {
        // Garantir que o código seja executado somente no lado do cliente
        if (event.phase == TickEvent.Phase.END) {
            ClientLevel clientLevel = Minecraft.getInstance().level;

            // Verifique se o nível atual é a dimensão vazia
            if (clientLevel != null && clientLevel.dimension() == EMPTY_DIMENSION) {
                // Remover as nuvens somente na dimensão vazia
                Minecraft.getInstance().options.cloudStatus().set(CloudStatus.OFF);
            }
        }
    }
}
