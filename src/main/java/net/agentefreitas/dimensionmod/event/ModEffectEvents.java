package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.effect.ModEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEffectEvents {

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            // Verifica se a entidade tem o teu efeito
            if (entity.hasEffect(ModEffects.PIG_CURSE_EFFECT.get())) {
                // Cancela o evento de colocar o bloco
                event.setCanceled(true);

                // Opcional: Avisar o jogador
                if (entity instanceof Player player && !player.level().isClientSide) {
                    //player.displayClientMessage(Component.literal("Estás amaldiçoado e não consegues construir!"), true);
                }
            }
        }
    }
}