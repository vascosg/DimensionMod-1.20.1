package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.custom.ArrayCenterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID) // Substitui pelo teu modid real
public class BoundarySecurityEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (shouldCancel(event.getPlayer(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        if (shouldCancel(event.getEntity(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (shouldCancel(event.getEntity(), event.getTarget().blockPosition())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (shouldCancel(event.getEntity(), event.getTarget().blockPosition())) {
            event.setCanceled(true);
        }
    }


    private static boolean shouldCancel(Player player, BlockPos targetPos) {
        // Percorremos apenas as barreiras que existem no servidor (super rápido)
        for (ArrayCenterBlockEntity barrier : ArrayCenterBlockEntity.ACTIVE_BARRIERS) {
            // Verifica se o player e a barreira estão no mesmo mundo/dimensão
            if (barrier.getLevel() != player.level()) continue;

            BlockPos center = barrier.getBlockPos();
            int radius = barrier.getRadius();

            // Posição relativa do Player ao centro (XZ)
            double px = player.getX() - (center.getX() + 0.5);
            double pz = player.getZ() - (center.getZ() + 0.5);

            // Posição relativa do Alvo ao centro (XZ)
            double tx = targetPos.getX() - center.getX();
            double tz = targetPos.getZ() - center.getZ();

            // Regra: Está dentro se |distancia| <= radius
            boolean playerInside = Math.abs(px) <= radius && Math.abs(pz) <= radius;
            boolean targetInside = Math.abs(tx) <= radius && Math.abs(tz) <= radius;

            // Se um estiver dentro e o outro fora, a interação é bloqueada
            if (playerInside != targetInside) {
                return true;
            }
        }
        return false;
    }

}
