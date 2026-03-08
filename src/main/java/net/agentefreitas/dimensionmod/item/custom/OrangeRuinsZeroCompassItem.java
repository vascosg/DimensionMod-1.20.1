package net.agentefreitas.dimensionmod.item.custom;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.saveddata.StructuresSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrangeRuinsZeroCompassItem extends Item {

    public OrangeRuinsZeroCompassItem(Properties properties) {
        super(properties);
    }

    public static BlockPos findNearestStructure(ServerLevel level, Player player) {
        BlockPos playerPos = player.blockPosition();

        // 1. Criamos a chave para a tua estrutura (usando o ID do teu JSON)
        ResourceLocation[] ids = {
                new ResourceLocation(DimensionMod.MOD_ID, "orange_r1"),
                new ResourceLocation(DimensionMod.MOD_ID, "orange_temple"),
                new ResourceLocation(DimensionMod.MOD_ID, "orange_village"),
        };

        BlockPos nearestPos = null;
        double shortestDistance = Double.MAX_VALUE;

        // 2. Loop para verificar cada uma
        for (ResourceLocation id : ids) {
            ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, id);
            var holder = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getHolder(key);

            if (holder.isPresent()) {
                // Procura a instância mais próxima desta estrutura específica
                var result = level.getChunkSource().getGenerator()
                        .findNearestMapStructure(level, HolderSet.direct(holder.get()),
                                playerPos, 100, false);

                if (result != null) {
                    BlockPos foundPos = result.getFirst();
                    // Calcula a distância ao quadrado até o jogador
                    double distanceSqr = foundPos.distSqr(playerPos);

                    // Se for a mais curta encontrada até agora, guarda-a
                    if (distanceSqr < shortestDistance) {
                        shortestDistance = distanceSqr;
                        nearestPos = foundPos;
                    }
                }
            }
        }

        // 3. Retorna a posição da estrutura que ganhou a "corrida" de proximidade
        return nearestPos;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // Só processamos no Servidor e se o item estiver selecionado (na mão)
        if (!level.isClientSide && isSelected && entity instanceof Player player) {
            // Verifica a cada 2 segundos (40 ticks) para poupar performance
            if (level.getGameTime() % 40 == 0) {
                BlockPos nearest = findNearestStructure((ServerLevel) level, player);
                if (nearest != null) {
                    stack.getOrCreateTag().putLong("TargetPos", nearest.asLong());
                }
            }
        }
    }

}