package net.agentefreitas.dimensionmod.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.BedBlock;

import java.util.Optional;

public class GoToBedAtNightGoal extends Goal {

    private final PathfinderMob mob;
    private final double speed;
    private BlockPos bedPos;

    public GoToBedAtNightGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        // Verifica se está de noite
        if (!mob.level().isNight()) return false;

        // Procura cama próxima (dentro de 10 blocos)
        Optional<BlockPos> optionalBed = findNearestBed();
        if (optionalBed.isPresent()) {
            bedPos = optionalBed.get();
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        if (bedPos != null) {
            mob.getNavigation().moveTo(bedPos.getX(), bedPos.getY(), bedPos.getZ(), speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return bedPos != null && !mob.getNavigation().isDone();
    }

    private Optional<BlockPos> findNearestBed() {
        BlockPos origin = mob.blockPosition();
        int r = 10;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-r, -2, -r), origin.offset(r, 2, r))) {
            if (mob.level().getBlockState(pos).is(BlockTags.BEDS)
                    && !mob.level().getBlockState(pos).getValue(BedBlock.OCCUPIED)) {
                return Optional.of(pos.immutable());
            }
        }
        return Optional.empty();
    }

    @Override
    public void stop() {
        bedPos = null;
        mob.setPose(Pose.STANDING);
    }
}
