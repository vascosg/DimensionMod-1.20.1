package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class GuardianBlockEntity extends BlockEntity {

    public GuardianBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GUARDIAN_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GuardianBlockEntity entity) {
        if (!level.isClientSide()) {
            if (level.getGameTime() % 400 == 0) {
                applyFatigue(level, pos);
            }
            if (!hasWaterNear(level, pos) && level.getGameTime() % 100 == 0) {
                level.removeBlock(pos, false);
            }
        } else {
            if (level.getGameTime() % 60 == 0) {
                sendParticlesToConduits(level, pos, 20);
            }
        }
    }

    private static Boolean hasWaterNear(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);


            if (neighborState.getFluidState().getType() == Fluids.WATER) {
                return true;
            }
        }
        return false;
    }

    private static void applyFatigue(Level level,BlockPos pos) {

        double radius = 10.0;
        AABB area = new AABB(pos).inflate(radius);

        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player player : players) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 1, true, true));
        }
    }

    public static List<BlockPos> findNearbyConduits(Level level, BlockPos center, int radius) {
        List<BlockPos> found = new ArrayList<>();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutablePos.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);

                    BlockEntity be = level.getBlockEntity(mutablePos);
                    if (be != null && be.getType() == BlockEntityType.CONDUIT) {
                        found.add(mutablePos.immutable());
                    }
                }
            }
        }

        return found;
    }

    public static void sendParticlesToConduits(Level level, BlockPos fromPos, int radius) {
        if (!level.isClientSide()) return;

        List<BlockPos> conduits = findNearbyConduits(level, fromPos, radius);

        double startX = fromPos.getX() + 0.5;
        double startY = fromPos.getY() + 0.5;
        double startZ = fromPos.getZ() + 0.5;

        for (BlockPos conduitPos : conduits) {
            double endX = conduitPos.getX() + 0.5;
            double endY = conduitPos.getY() + 0.5;
            double endZ = conduitPos.getZ() + 0.5;

            double dx = endX - startX;
            double dy = endY - startY;
            double dz = endZ - startZ;

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            int steps = (int) (distance * 3); // controla a densidade de partículas

            double stepX = dx / steps;
            double stepY = dy / steps;
            double stepZ = dz / steps;

            for (int i = 0; i <= steps; i++) {
                double px = startX + stepX * i;
                double py = startY + stepY * i;
                double pz = startZ + stepZ * i;

                level.addParticle(ParticleTypes.BUBBLE, px, py, pz, 0, 0, 0);
            }
        }
    }


}
