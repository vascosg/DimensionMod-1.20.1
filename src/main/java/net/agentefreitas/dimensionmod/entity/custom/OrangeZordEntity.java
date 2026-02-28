package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.goal.OrangePigAttackGoal;
import net.agentefreitas.dimensionmod.entity.goal.OrangeZordAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrangeZordEntity extends TamableAnimal {

    private Double targetY = null;

    public OrangeZordEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(OrangeZordEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

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
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.getTags().contains("rising_from_factory")) {
            if (targetY == null) {
                targetY = this.getY() + 1.2D;
            }

            if (this.getY() < targetY) {
                this.setDeltaMovement(0, 0.05, 0);
                this.hasImpulse = true;
                this.setNoGravity(true);
            } else {
                // --- CHEGOU AO TOPO ---
                this.setDeltaMovement(0, 0, 0);
                this.setNoGravity(false);
                this.removeTag("rising_from_factory");

                if (!this.level().isClientSide) {
                    BlockPos pos = this.blockPosition();

                    // 1. Remove o bloco da fábrica (transforma em Ar)
                    // Se o Zord subiu 1.2, ele está tecnicamente um bloco acima da fábrica agora
                    // Por isso, limpamos o bloco que está logo abaixo dele
                    this.level().removeBlock(pos.below(), false);

                    // 2. Procurar a entidade visual da fábrica para a remover
                    // Procuramos num raio pequeno para não apagar fábricas vizinhas
                    List<OrangeZordFactoryEntity> factories = this.level().getEntitiesOfClass(
                            OrangeZordFactoryEntity.class, this.getBoundingBox().inflate(2));

                    for (OrangeZordFactoryEntity factory : factories) {
                        factory.discard(); // Adeus fábrica!
                    }

                }
                this.targetY = null;
            }
        }
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new OrangeZordAttackGoal(this, 1.0D, true));

        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, false));

        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));

        addBehaviourGoals();
    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, true));

    }

    private void setupAnimationStates() {
        if(this.isAttacking()) {
            this.idleAnimationState.stop(); // Para o idle para não encavalar
            if(attackAnimationTimeout <= 0) {
                attackAnimationTimeout = 15;
                attackAnimationState.start(this.tickCount);
            }
        } else {
            --this.attackAnimationTimeout;
            // Lógica do Idle original...
            if(this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        // Se o alvo for o próprio dono, o Zord recusa-se a atacar
        if (pTarget == pOwner) {
            return false;
        }
        return super.wantsToAttack(pTarget, pOwner);
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        // Se o Zord for domesticado, ele é aliado do dono
        if (this.isTame() && pEntity instanceof LivingEntity) {
            return pEntity.getUUID().equals(this.getOwnerUUID()) || super.isAlliedTo(pEntity);
        }
        return super.isAlliedTo(pEntity);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 15.0D);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isOwnedBy(player)) {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }
}
