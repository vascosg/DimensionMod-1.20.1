package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OverworldBailerItem extends Item {
    public OverworldBailerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Apenas processamos no servidor
        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
            // 1. Obtemos a instância do Overworld
            ServerLevel overworld = pLevel.getServer().getLevel(Level.OVERWORLD);

            if (overworld != null) {
                // 2. Obtemos o ponto de Spawn global do mundo
                BlockPos spawnPos = overworld.getSharedSpawnPos();

                // 3. Teleportamos o jogador (coordenadas centrais do bloco + altura segura)
                serverPlayer.teleportTo(overworld,
                        spawnPos.getX() + 0.5,
                        spawnPos.getY() + 1.0,
                        spawnPos.getZ() + 0.5,
                        serverPlayer.getYRot(),
                        serverPlayer.getXRot()
                );

                // 4. Efeitos visuais e sonoros no destino
                overworld.playSound(null, spawnPos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

                // 5. Cooldown para evitar spam (ex: 20 segundos)
                //pPlayer.getCooldowns().addCooldown(this, 400);

                 itemstack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}