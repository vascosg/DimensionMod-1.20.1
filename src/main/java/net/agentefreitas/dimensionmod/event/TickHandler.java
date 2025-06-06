package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.block.custom.DimensionBookLecternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.server.level.ChunkHolder;

@Mod.EventBusSubscriber
public class TickHandler {
    private static final int CHUNK_RADIUS = 5; // Ajuste o raio conforme quiser

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && DimensionBookLecternBlockEntity.getAccelerateTicks()) {
            for (ServerLevel level : event.getServer().getAllLevels()) {
                for (ServerPlayer player : level.players()) {
                    ChunkPos playerChunkPos = new ChunkPos(player.blockPosition());

                    for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
                        for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                            ChunkPos chunkPos = new ChunkPos(playerChunkPos.x + dx, playerChunkPos.z + dz);

                            ChunkAccess chunkAccess = level.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, false);

                            if (chunkAccess instanceof LevelChunk chunk) {
                                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

                                for (int x = 0; x < 16; x++) {
                                    for (int z = 0; z < 16; z++) {
                                        for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                                            pos.set(chunkPos.getMinBlockX() + x, y, chunkPos.getMinBlockZ() + z);
                                            BlockState state = chunk.getBlockState(pos);

                                            if (state.isRandomlyTicking()) {
                                                for (int i = 0; i < 4; i++) {
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
    }


}