package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class GuardianDoorBlockEntity extends BlockEntity {



    public GuardianDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GUARDIAN_DOOR_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GuardianDoorBlockEntity entity) {
        if (!level.isClientSide()) {
            boolean shouldOpen = !hasGuardianBlockAtDistance(level, pos, 20);
            BlockState current = level.getBlockState(pos);
            boolean isOpen = current.getValue(GuardianDoorBlock.OPEN);


            if (shouldOpen && !isOpen) {
                level.setBlock(pos, current.setValue(GuardianDoorBlock.OPEN, true), 3);
            } else if (!shouldOpen && isOpen) {
                level.setBlock(pos, current.setValue(GuardianDoorBlock.OPEN, false), 3);
            }
        }
    }

    private static boolean hasGuardianBlockAtDistance(Level level, BlockPos pos, int distance) {
        BlockPos[] positionsToCheck = {
                pos.offset(distance, 0, 0),   // +X
                pos.offset(-distance, 0, 0),  // -X
                pos.offset(0, 0, distance),   // +Z
                pos.offset(0, 0, -distance)   // -Z
        };

        for (BlockPos checkPos : positionsToCheck) {
            BlockState checkState = level.getBlockState(checkPos);
            if (checkState.is(ModBlocks.GUARDIAN_BLOCK.get())) {
                return true;
            }
        }
        return false;
    }

}
