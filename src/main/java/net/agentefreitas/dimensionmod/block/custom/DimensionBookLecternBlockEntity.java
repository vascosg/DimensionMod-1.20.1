package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class DimensionBookLecternBlockEntity extends BlockEntity {

        private CompoundTag bookNBT = new CompoundTag(); // Armazena o NBT do livro

        public DimensionBookLecternBlockEntity(BlockPos pos, BlockState state) {
            super(ModBlockEntities.DIMENSION_BOOK_LECTERN_BLOCK_ENTITY.get(), pos, state);
        }

        // Métodos para salvar e carregar o conteúdo do livro
        public void setBookNBT(CompoundTag nbt) {
            this.bookNBT = nbt;
        }

        public CompoundTag getBookNBT() {
            return this.bookNBT;
        }

        @Override
        public void saveAdditional(CompoundTag compound) {
            super.saveAdditional(compound);
            compound.put("BookNBT", bookNBT); // Salva o NBT do livro no BlockEntity
        }

        @Override
        public void load(CompoundTag compound) {
            super.load(compound);
            if (compound.contains("BookNBT", 10)) {
                this.bookNBT = compound.getCompound("BookNBT"); // Carrega o NBT do livro
            }
        }
}
