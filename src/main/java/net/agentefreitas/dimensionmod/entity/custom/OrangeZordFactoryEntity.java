package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.block.custom.OrangeZordFactoryBlockEntity;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;


public class OrangeZordFactoryEntity extends Entity {

    private UUID ownerUUID;
    private static final EntityDataAccessor<Boolean> IS_OPENING =
            SynchedEntityData.defineId(OrangeZordFactoryEntity.class, EntityDataSerializers.BOOLEAN);

    private int animationTick = -1;

    public final AnimationState openingAnimationState = new AnimationState();
    private int openingAnimationTimeout = 0;

    public OrangeZordFactoryEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(IS_OPENING, false);
    }

    public void startOpening() {
        this.entityData.set(IS_OPENING, true);
    }

    public boolean isOpening() {
        return this.entityData.get(IS_OPENING);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        // Necessário para a entidade aparecer no cliente (Network)
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            // Se ainda não temos o dono, vamos buscá-lo ao bloco
            if (this.ownerUUID == null) {
                BlockEntity be = this.level().getBlockEntity(this.blockPosition());
                if (be instanceof OrangeZordFactoryBlockEntity factoryBe) {
                    if (factoryBe.getOwner() != null) {
                        this.setOwner(factoryBe.getOwner());
                    }
                }
            }

            if (this.isOpening()) {
                // Se o timer ainda é -1, significa que o clique acabou de acontecer
                if (this.animationTick == -1) {
                    this.animationTick = 0;
                }

                // O timer só conta se for maior ou igual a 0
                if (this.animationTick >= 0 && this.animationTick < 200) {
                    this.animationTick++;

                    // Debug para veres o contador no log
                    // System.out.println("Timer: " + this.animationTick);

                    if (this.animationTick == 50) {
                        spawnZordInside(); // O "hey" deve aparecer agora!
                    }
                }
            } else {
                this.animationTick = -1;
            }
        }

        if (this.level().isClientSide()) {
            // Lógica de animação (similar à que fizeste no OrangeZord)
            if (this.isOpening()) {
                if (openingAnimationTimeout <= 0) {
                    openingAnimationTimeout = 120; // Duração da animação em ticks
                    openingAnimationState.start(this.tickCount);
                }
            } else {
                openingAnimationState.stop();
                openingAnimationTimeout = 0;
            }
        }
    }

    public void setOwner(UUID owner) { this.ownerUUID = owner; }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.ownerUUID != null) {
            pCompound.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.hasUUID("OwnerUUID")) {
            this.ownerUUID = pCompound.getUUID("OwnerUUID");
        }
    }

    private void spawnZordInside() {
        if (!this.level().isClientSide()) {
            OrangeZordEntity zord = ModEntities.ORANGE_ZORD.get().create(this.level());
            if (zord != null) {
                zord.moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                zord.addTag("rising_from_factory");
                zord.setNoGravity(true);

                if (this.ownerUUID != null) {
                    System.out.println("hey");
                    zord.setOwnerUUID(this.ownerUUID);
                    zord.setTame(true);
                }

                this.level().addFreshEntity(zord);
            }
        }
    }

}
