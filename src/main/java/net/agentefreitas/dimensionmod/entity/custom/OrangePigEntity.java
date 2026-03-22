package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.effect.ModEffects;
import net.agentefreitas.dimensionmod.entity.goal.*;
import net.agentefreitas.dimensionmod.packet.Messages;
import net.agentefreitas.dimensionmod.packet.PigPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class OrangePigEntity extends PathfinderMob implements Npc {


    public OrangePigEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPersistenceRequired();
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID =
            SynchedEntityData.defineId(OrangePigEntity.class, EntityDataSerializers.INT);

    public static final int MELEE_VARIANT = 0;
    public static final int RANGED_VARIANT = 1;
    public static final int DROPPER_VARIANT = 2;

    private static final EntityDataAccessor<Integer> ATTACK_TYPE =
            SynchedEntityData.defineId(OrangePigEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(OrangePigEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public final AnimationState throwAnimationState = new AnimationState();
    public int throwAnimationTimeout = 0;

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
                                        @Nullable CompoundTag pDataTag) {
        // Exemplo: 50% de chance de ser cada um
        this.setVariant(this.random.nextInt(3));

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        } else {
            if (this.getVariant() == DROPPER_VARIANT) {
                givePigCurse();
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(0, new OpenNearByDoorGoal(this, 5,10));
        //this.goalSelector.addGoal(1, new GoToBedAtNightGoal(this, 1.0));
        //this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(ModItems.CAT_NEEP), false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        addBehaviourGoals();
    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.goalSelector.addGoal(2, new OrangePigThrowGoal(this, 1.25D, 25));
        this.goalSelector.addGoal(2, new OrangePigAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new OrangePigDropItemGoal(this));

    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isAttacking()) {
            if (this.getAttackType() == 0) { // MELEE (ATTACK)
                if (attackAnimationTimeout <= 0) {
                    attackAnimationTimeout = 10; // Length in ticks of your animation
                    attackAnimationState.start(this.tickCount);
                }
            } else if (this.getAttackType() == 1) { // RANGED (THROW)
                if (throwAnimationTimeout <= 0) {
                    throwAnimationTimeout = 20; // Length in ticks of your animation
                    throwAnimationState.start(this.tickCount);
                }
            }
        } else {
            attackAnimationState.stop();
            throwAnimationState.stop();
        }

        // Decrementar timeouts
        if (attackAnimationTimeout > 0) --attackAnimationTimeout;
        if (throwAnimationTimeout > 0) --throwAnimationTimeout;

    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void startAttack(int type) {
        this.entityData.set(ATTACKING, true);
        this.entityData.set(ATTACK_TYPE, type);
    }

    public int getAttackType() {
        return this.entityData.get(ATTACK_TYPE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(ATTACK_TYPE, 0);
        this.entityData.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(pCompound.getInt("Variant"));
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }


    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            // 1. Dá velocidade ao movimento relativo (0.1F é o padrão, 0.15F+ é rápido)
            this.moveRelative(0.15F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());

            // 2. Aplica fricção da água (0.9 é o padrão, quanto maior, mais ele desliza)
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));

            // 3. Impede que ele afunde demasiado rápido
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            // Se não estiver na água, usa o movimento normal de terra
            super.travel(pTravelVector);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }


    public void triggerPigCurse(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            // Envia o pacote para o ecrã do jogador
            Messages.sendToPlayer(new PigPacket(), serverPlayer);

            serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                    SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.HOSTILE, 1.0F, 1.0F);
        }
    }

    private void givePigCurse () {

        // A cada 1 segundo (20 ticks), verifica se deve dar o susto
        if (!this.level().isClientSide && this.tickCount % 20 == 0) {
            Player player = this.level().getNearestPlayer(this, 20.0D);

            // 1. Verifica se o player existe
            if (player != null) {
                // 2. Verifica se o jogador JÁ TEM o efeito do porco
                // Usamos o teu RegistryObject: PIG_CURSE_EFFECT.get()
                boolean temOFeito = player.hasEffect(ModEffects.PIG_CURSE_EFFECT.get());

                // 3. Se NÃO tiver o efeito, faz o susto e aplica o efeito
                if (!temOFeito) {
                    this.triggerPigCurse(player);

                    // Adiciona o efeito por 20 segundos (20 segundos * 20 ticks = 400 ticks)
                    // O '0' é o amplificador (Nível 1)
                    player.addEffect(new MobEffectInstance(ModEffects.PIG_CURSE_EFFECT.get(), 400, 0));

                }
            }
        }

    }
}
