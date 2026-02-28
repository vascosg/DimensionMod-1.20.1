package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordFactoryEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class OrangeZordFactoryBlockEntity extends BlockEntity {
    // Esta variável vai guardar a referência da "entidade visual" do Animated Java
    private OrangeZordFactoryEntity visualEntity;

    private UUID ownerUUID;

    public OrangeZordFactoryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ORANGE_ZORDE_FACTORY_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OrangeZordFactoryBlockEntity be) {
        if (!level.isClientSide) {
            // SÓ ENTRA SE TIVERMOS O UUID DO DONO
            if (be.ownerUUID != null) {
                if (be.visualEntity == null || !be.visualEntity.isAlive()) {
                    be.spawnVisualEntity(level, pos);
                }
            }
        }
    }

    private void spawnVisualEntity(Level level, BlockPos pos) {
        OrangeZordFactoryEntity entity = ModEntities.ORANGE_ZORD_FACTORY.get().create(level);
        if (entity != null) {
            entity.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0, 0);

            // --- AQUI ESTAVA O BURACO ---
            // Passamos o UUID que a BlockEntity guardou para a Entidade Visual
            entity.setOwner(this.ownerUUID);

            System.out.println("2. UUID passado para a Entidade Visual: " + this.ownerUUID);

            level.addFreshEntity(entity);
            this.visualEntity = entity;
        }
    }

    public void triggerAnimation() {
        if (this.visualEntity != null && this.visualEntity.isAlive()) {
            this.visualEntity.startOpening();
        } else {
            // Caso a entidade tenha sumido, tenta procurá-la por perto
            List<OrangeZordFactoryEntity> entities = this.level.getEntitiesOfClass(
                    OrangeZordFactoryEntity.class, new AABB(this.worldPosition).inflate(1));

            if (!entities.isEmpty()) {
                this.visualEntity = entities.get(0);
                this.visualEntity.startOpening();
            }
        }
    }

    @Override
    public void setRemoved() {
        if (visualEntity != null) {
            visualEntity.discard();
        }
        super.setRemoved();
    }

    @Override
    public void onChunkUnloaded() {
        if (visualEntity != null) {
            visualEntity.discard();
        }
    }

    public void setVisualEntity(OrangeZordFactoryEntity entity) {
        this.visualEntity = entity;
    }

    public void removeVisualEntity() {
        if (this.visualEntity != null && this.visualEntity.isAlive()) {
            this.visualEntity.discard();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (ownerUUID != null) pTag.putUUID("Owner", ownerUUID);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.hasUUID("Owner")) ownerUUID = pTag.getUUID("Owner");
    }

    public void setOwner(UUID owner) { this.ownerUUID = owner; }
    public UUID getOwner() { return ownerUUID; }
}