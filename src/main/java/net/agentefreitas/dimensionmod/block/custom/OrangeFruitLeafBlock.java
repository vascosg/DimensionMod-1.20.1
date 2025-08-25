package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.OrangeFruitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class OrangeFruitLeafBlock extends LeavesBlock {
    public OrangeFruitLeafBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (random.nextFloat() < 0.0001f) {

            OrangeFruitEntity fruta = new OrangeFruitEntity(ModEntities.ORANGE_FRUIT.get(), world);

            double x = pos.getX() + 0.5;
            double y = pos.getY() - 0.25;
            double z = pos.getZ() + 0.5;

            fruta.setPos(x, y, z);
            System.out.println("Spawning orange...");
            if (world.isEmptyBlock(pos.below())) {
                world.addFreshEntity(fruta);
            }
        }
    }
}
