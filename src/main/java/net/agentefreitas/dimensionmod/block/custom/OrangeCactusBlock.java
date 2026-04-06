package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;

public class OrangeCactusBlock extends CactusBlock {
    public OrangeCactusBlock(Properties p_51136_) {
        super(p_51136_);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(level.damageSources().cactus(), 1.0F);

        // 2. Verifica se a entidade pode ser queimada (não é imune a fogo)
        if (!entity.fireImmune()) {
            // 3. Define a entidade em chamas por X segundos (ex: 5 segundos)
            entity.setSecondsOnFire(5);

            // 4. Podes até dar um dano extra de fogo imediato
            entity.hurt(level.damageSources().inFire(), 1.0F);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.relative(facing));
        // Permite que o próprio OrangeCactusBlock seja plantado em cima de outro igual
        if (plant.is(this)) {
            return true;
        }
        // Mantém a lógica normal (permitir areia, etc)
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos abovePos = pos.above();
        if (level.isEmptyBlock(abovePos)) {
            int height;
            // Conta quantos blocos IGUAIS a este existem para baixo
            for (height = 1; level.getBlockState(pos.below(height)).is(this); ++height) {
            }

            if (height < 3) {
                int age = state.getValue(AGE);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, abovePos, state, true)) {
                    if (age == 15) {
                        // Aqui está o segredo: coloca o TEU bloco e não o cacto verde
                        level.setBlockAndUpdate(abovePos, this.defaultBlockState());
                        BlockState newState = state.setValue(AGE, 0);
                        level.setBlock(pos, newState, 4);
                        newState.neighborChanged(level, abovePos, this, pos, false);
                    } else {
                        level.setBlock(pos, state.setValue(AGE, age + 1), 4);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }
}
