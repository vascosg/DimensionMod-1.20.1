package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.mixin.ChunkMapAccessor;
import net.agentefreitas.dimensionmod.block.custom.DimensionBookLecternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.server.level.ChunkHolder;

@Mod.EventBusSubscriber
public class TickHandler {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && DimensionBookLecternBlockEntity.getAccelerateTicks()) {
            for (ServerLevel level : event.getServer().getAllLevels()) {
                // Obter todos os ChunkHolder (chunks carregados)

                ChunkMap chunkMap = level.getChunkSource().chunkMap;
                Long2ObjectLinkedOpenHashMap<ChunkHolder> visibleChunks = ((ChunkMapAccessor) (Object) chunkMap).getVisibleChunkMap();


                for (ChunkHolder holder : visibleChunks.values()) {
                    LevelChunk chunk = holder.getTickingChunk();

                    if (chunk != null) {
                        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                                    pos.set(chunk.getPos().getMinBlockX() + x, y, chunk.getPos().getMinBlockZ() + z);
                                    BlockState state = chunk.getBlockState(pos);

                                    if (state.isRandomlyTicking()) {
                                        for (int i = 0; i < 4; i++) {  // 4 ticks extras para outros blocos que usam random ticks
                                            state.randomTick(level, pos, level.random);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}