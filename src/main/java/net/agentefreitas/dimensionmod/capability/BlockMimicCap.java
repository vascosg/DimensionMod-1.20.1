package net.agentefreitas.dimensionmod.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;

public class BlockMimicCap implements IBlockMimicCap, INBTSerializable<CompoundTag> {
    private BlockState mimicBlock = Blocks.AIR.defaultBlockState();
    private int ticksRemaining = 0;

    @Override
    public void setMimicBlock(BlockState state, int ticksRemaining) {
        this.mimicBlock = state;
        this.ticksRemaining = ticksRemaining;
    }

    @Override
    public BlockState getMimicBlock() {
        return this.mimicBlock;
    }

    @Override
    public int getTicksRemaining() {
        return this.ticksRemaining;
    }

    @Override
    public void tick() {
        if (this.ticksRemaining > 0) {
            this.ticksRemaining--;
            if (this.ticksRemaining <= 0) {
                this.mimicBlock = Blocks.AIR.defaultBlockState(); // Reseta quando acaba
            }
        }
    }

    // Para guardar/carregar se o jogador deslogar
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("BlockState", NbtUtils.writeBlockState(this.mimicBlock));
        tag.putInt("TicksRemaining", this.ticksRemaining);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.ticksRemaining = nbt.getInt("TicksRemaining");

        if (nbt.contains("BlockState", 10)) {
            // Corrigido: Usamos o BuiltInRegistries para ler o bloco sem precisar da instância do Minecraft.level
            this.mimicBlock = NbtUtils.readBlockState(net.minecraft.core.registries.BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("BlockState"));
        } else {
            this.mimicBlock = Blocks.AIR.defaultBlockState();
        }
    }
}