package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomPortalBlockEntity extends BlockEntity {
    public CustomPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_PORTAL_BLOCK_ENTITY.get(), pos, state);
    }


}
