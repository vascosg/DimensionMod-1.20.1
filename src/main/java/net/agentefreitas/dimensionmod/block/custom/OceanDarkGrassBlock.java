package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class OceanDarkGrassBlock extends FlowerBlock {
    public OceanDarkGrassBlock(Supplier<MobEffect> effect, int duration, BlockBehaviour.Properties properties) {
        super(effect, duration, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.BLACKSTONE);
    }
}
