package net.agentefreitas.dimensionmod.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.util.RandomSource;

public class MobEntitySpawnPlacements {

    public static boolean checkDiscipleSpawnRules(EntityType<? extends PathfinderMob> entityType,
                                                  ServerLevelAccessor world,
                                                  MobSpawnType spawnReason,
                                                  BlockPos pos,
                                                  RandomSource random) {
        // Desativa a verificação de luz
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        BlockState below = world.getBlockState(pos.below());
        return below.isSolid();
    }

    public static boolean checkLittlePrupleSpawnRules(EntityType<? extends PathfinderMob> entityType,
                                                  ServerLevelAccessor world,
                                                  MobSpawnType spawnReason,
                                                  BlockPos pos,
                                                  RandomSource random) {

        BlockState below = world.getBlockState(pos.below());
        return below.isSolid();
    }
}