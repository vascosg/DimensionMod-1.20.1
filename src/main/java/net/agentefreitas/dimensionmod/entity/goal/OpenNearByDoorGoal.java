package net.agentefreitas.dimensionmod.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;

public class OpenNearByDoorGoal extends Goal {
    private final PathfinderMob mob;
    private final int searchInterval;   // quantos ticks entre verificações
    private final int closeDelay;       // quanto tempo a porta fica aberta
    private int nextCheck = 0;

    private BlockPos lastDoor;          // última porta aberta
    private int closeTimer = 0;

    public OpenNearByDoorGoal(PathfinderMob mob, int checkEveryTicks, int closeDelayTicks) {
        this.mob = mob;
        this.searchInterval = checkEveryTicks;
        this.closeDelay = closeDelayTicks;
    }

    @Override
    public boolean canUse() {
        // Ativa se houver porta para fechar (já aberta) ou se for altura de procurar outra
        return lastDoor != null || mob.tickCount >= nextCheck;
    }

    @Override
    public boolean canContinueToUse() {
        // Continua ativo enquanto houver porta para fechar
        return lastDoor != null;
    }

    @Override
    public void tick() {
        if (mob.level().isClientSide) return;

        // Se já temos uma porta aberta, faz a contagem para fechar
        if (lastDoor != null) {
            if (--closeTimer <= 0) {
                BlockState state = mob.level().getBlockState(lastDoor);
                if (state.getBlock() instanceof DoorBlock && state.getValue(DoorBlock.OPEN)) {
                    mob.level().setBlock(lastDoor, state.setValue(DoorBlock.OPEN, false), 3);
                }
                lastDoor = null; // já fechou
            }
            return; // não procura novas portas enquanto esta não for fechada
        }

        // Caso contrário, a cada searchInterval procura outra porta
        if (mob.tickCount >= nextCheck) {
            nextCheck = mob.tickCount + searchInterval;
            BlockPos base = mob.blockPosition();
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos pos = base.relative(dir);
                if (tryOpenDoor(pos)) return;
                if (tryOpenDoor(pos.above())) return;
            }
        }
    }

    private boolean tryOpenDoor(BlockPos pos) {
        BlockState state = mob.level().getBlockState(pos);
        if (state.getBlock() instanceof DoorBlock && !state.getValue(DoorBlock.OPEN)) {
            mob.level().setBlock(pos, state.setValue(DoorBlock.OPEN, true), 3);
            // guarda para fechar depois
            lastDoor = pos.immutable();
            closeTimer = closeDelay;
            return true;
        }
        return false;
    }
}
