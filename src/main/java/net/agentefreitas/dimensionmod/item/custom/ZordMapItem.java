package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.Optional;

public class ZordMapItem extends MapItem {

    public ZordMapItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            BlockPos playerPos = player.blockPosition();

            // Obtém o Holder do fóssil de forma segura
            var registry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
            Optional<Holder.Reference<Structure>> villageHolderOpt = registry.getHolder(
                    ResourceKey.create(Registries.STRUCTURE, new ResourceLocation("minecraft", "village/plains"))
            );

            if (villageHolderOpt.isPresent()) { // <-- valida o Optional
                Holder<Structure> fossilHolder = villageHolderOpt.get();
                HolderSet<Structure> holderSet = HolderSet.direct(fossilHolder);

                var result = serverLevel.getChunkSource().getGenerator()
                        .findNearestMapStructure(serverLevel, holderSet, playerPos, 1000, false);
                if (result != null) {
                    BlockPos target = result.getFirst();

                    // cria o mapa
                    ItemStack map = MapItem.create(serverLevel,
                            target.getX(), target.getZ(),
                            (byte) 2, true, true);

                    // obtém os dados salvos do mapa
                    MapItemSavedData data = MapItem.getSavedData(map, serverLevel);

                    if (data != null) {
                        // adiciona o “X” vermelho
                        data.addTargetDecoration(map, target, "+", MapDecoration.Type.RED_X);
                    }

                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    player.getInventory().add(map);
                }
            } else {
                player.sendSystemMessage(Component.literal("Nenhum fóssil encontrado!"));
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
