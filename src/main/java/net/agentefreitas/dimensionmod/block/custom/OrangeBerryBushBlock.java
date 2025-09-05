package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class OrangeBerryBushBlock extends FlowerBlock {
    public OrangeBerryBushBlock(Supplier<MobEffect> effect, int duration, Properties properties) {
        super(effect, duration, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(Blocks.DIRT) ||
                state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.FARMLAND) ||
                state.is(Blocks.CYAN_TERRACOTTA) ||
                state.is(Blocks.GREEN_TERRACOTTA) ||
                state.is(Blocks.LIME_TERRACOTTA) ||
                state.is(Blocks.BROWN_TERRACOTTA);
    }
}