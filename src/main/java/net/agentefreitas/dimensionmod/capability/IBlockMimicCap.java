package net.agentefreitas.dimensionmod.capability;

import net.minecraft.world.level.block.state.BlockState;

public interface IBlockMimicCap {
    void setMimicBlock(BlockState state, int ticksRemaining);
    BlockState getMimicBlock();
    int getTicksRemaining();
    void tick(); // Para diminuir o tempo
}