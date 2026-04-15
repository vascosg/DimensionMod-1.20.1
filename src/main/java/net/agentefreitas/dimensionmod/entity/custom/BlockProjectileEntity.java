package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.effect.ModEffects;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.packet.ClientboundSyncBlockMimicPacket;
import net.agentefreitas.dimensionmod.packet.Messages;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;

public class BlockProjectileEntity extends ThrowableItemProjectile {
    // 1. Define o DataAccessor para o BlockState
    private static final EntityDataAccessor<Optional<BlockState>> MIMIC_BLOCK =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);

    private boolean hasCheckedBlock = false;

    public BlockProjectileEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    public BlockProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.BLOCK_PROJECTILE.get(), shooter, level);
        // Quando disparado por um Mob, pegamos logo o bloco abaixo do Mob
        this.setMimicBlock(level.getBlockState(shooter.blockPosition().below()));
        this.hasCheckedBlock = true;
    }

    @Override
    public void tick() {
        super.tick();

        // Se a entidade foi criada por comando (/summon), ela não tem shooter.
        // Então, no primeiro tick, ela checa o que está abaixo dela mesma.
        if (!this.level().isClientSide && !hasCheckedBlock) {
            BlockState stateBelow = this.level().getBlockState(this.blockPosition().below());

            // Se estiver no ar, procura o primeiro bloco sólido num raio de 5 blocos abaixo
            if (stateBelow.isAir()) {
                for (int i = 1; i <= 5; i++) {
                    BlockState found = this.level().getBlockState(this.blockPosition().below(i));
                    if (!found.isAir()) {
                        stateBelow = found;
                        break;
                    }
                }
            }

            this.setMimicBlock(stateBelow);
            this.hasCheckedBlock = true;
        }
    }


    // Função auxiliar para achar bloco sólido (opcional, para quando a galinha está a voar)
    private BlockState findSolidBlockBelow(Level level, BlockPos startPos) {
        for (int i = 0; i < 5; i++) { // Procura até 5 blocos para baixo
            BlockState state = level.getBlockState(startPos.below(i));
            if (!state.isAir()) {
                return state;
            }
        }
        return Blocks.DIRT.defaultBlockState(); // Fallback se não achar nada
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        // Inicializa com DIRT como padrão
        this.entityData.define(MIMIC_BLOCK, Optional.of(Blocks.DIRT.defaultBlockState()));
    }

    // Getters e Setters para o DataAccessor
    public void setMimicBlock(BlockState state) {
        this.entityData.set(MIMIC_BLOCK, Optional.of(state));
    }

    public BlockState getMimicBlock() {
        return this.entityData.get(MIMIC_BLOCK).orElse(Blocks.DIRT.defaultBlockState());
    }

    // Dentro do teu BlockProjectileEntity.java

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && result.getEntity() instanceof ServerPlayer serverPlayer) {
            // 1. Aplica o dano normal
            serverPlayer.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);

            // 2. Pega o bloco que o projétil está a mimetizar
            BlockState currentBlock = this.getMimicBlock();

            // 3. Envia o pacote para o Cliente do jogador atingido (3 segundos = 60 ticks)
            Messages.sendToPlayer(new ClientboundSyncBlockMimicPacket(currentBlock, 60), serverPlayer);

            // Opcional: Efeitos sonoros
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), currentBlock.getSoundType().getBreakSound(), SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            // Efeito de partículas de quebra de bloco ao bater
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    // Adiciona este método para carregar o bloco via comando/save file
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("BlockState", 10)) {
            this.setMimicBlock(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), pCompound.getCompound("BlockState")));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("BlockState", NbtUtils.writeBlockState(this.getMimicBlock()));
    }
}