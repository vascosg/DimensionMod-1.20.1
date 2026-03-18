package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MobFigureBlockEntity extends BlockEntity {
    private String mobId = "minecraft:pig";
    private CompoundTag mobData;// Valor padrão

    public MobFigureBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOB_FIGURE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        // Só atualizamos se o NBT trouxer um ID novo.
        // Se pTag não tiver MobId, mantemos o que já lá estava!
        if (pTag.contains("MobId")) {
            this.mobId = pTag.getString("MobId");
            System.out.println("[DEBUG-BLOCK] MobId definido para: " + this.mobId);
        }

        if (pTag.contains("MobData")) {
            this.mobData = pTag.getCompound("MobData");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        // DEBUG: Ver o que está a ser salvo no disco
        System.out.println("[DEBUG-SAVE] Salvando no NBT: " + this.mobId);

        pTag.putString("MobId", this.mobId); // ESSENCIAL! Estava em falta.

        if (this.mobData != null) {
            pTag.put("MobData", this.mobData);
        }
    }


    // Cria um Getter para o Renderer usar
    public CompoundTag getMobData() { return this.mobData; }

    public void setMobId(String id) {
        System.out.println("[DEBUG-SET] Alterando MobId de " + this.mobId + " para " + id);
        this.mobId = id;
        setChanged();
        // Força o envio de dados para o cliente imediatamente
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getMobId() { return mobId; }

    // Sincronização importante para o Renderer ver o mob


    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        System.out.println("[DEBUG-SYNC] Enviando tag para o Cliente: " + tag.getString("MobId"));
        return tag;
    }

    // 2. Cria o pacote de rede que viaja do servidor para o teu PC
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // 3. Recebe os dados no cliente e atualiza o renderer
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            load(tag); // Carrega o MobId e MobData para o Renderer usar
        }
    }
}