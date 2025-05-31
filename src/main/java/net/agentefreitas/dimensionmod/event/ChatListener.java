package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.block.custom.DimensionBookLectern;
import net.agentefreitas.dimensionmod.block.custom.DimensionBookLecternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ChatListener {

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getMessage().getString();
        BlockPos playerPos = player.blockPosition();
        // Raio de 3 blocos
        int range = 10;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos blockPos = playerPos.offset(x, y, z);

                    Block block = player.level().getBlockState(blockPos).getBlock();
                    if (block instanceof DimensionBookLectern) {
                        BlockEntity entity = ((DimensionBookLectern) block).getDimensionBlockEntity();

                        if (entity instanceof DimensionBookLecternBlockEntity lectern) {
                            lectern.messageReceiver(message, player);
                            System.out.println("Bloco ouviu: " + message);
                        }
                    }
                }
            }
        }
    }
}
