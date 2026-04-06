package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomPortalBlockEntity extends BlockEntity {
    // Valores padrão
    private ResourceLocation destinationDim = new ResourceLocation("minecraft", "overworld");

    public CustomPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CUSTOM_PORTAL_BLOCK_ENTITY.get(), pos, state);
    }

    // Método para configurar o portal após o spawn
    public void setPortalData(ResourceLocation dim) {
        this.destinationDim = dim;
        this.setChanged(); // Marca para salvar no disco
    }

    public ResourceLocation getDestinationDim() {
        return destinationDim;
    }

    // --- SALVAR E CARREGAR (NBT) ---

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putString("destination", destinationDim.toString());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("destination")) {
            this.destinationDim = new ResourceLocation(nbt.getString("destination"));
        }
    }

    // --- SINCRONIZAÇÃO COM O CLIENTE (Para a Renderização) ---

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


}
