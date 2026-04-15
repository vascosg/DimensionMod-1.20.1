package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.goal.RabbitFireOrbitGoal;
import net.agentefreitas.dimensionmod.entity.goal.RabbitHopGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FireRabbitEntity extends PathfinderMob {
    public FireRabbitEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {

        super(p_21683_, p_21684_);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(FireRabbitEntity.class, EntityDataSerializers.BOOLEAN);

    // Variáveis para a animação do círculo expansivo (apenas cliente)
    private int expansionTick = 0;
    private static final int MAX_EXPANSION_TICKS = 3; // Duração da animação (0.4 segundos)

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        // Lógica da onda de choque expansiva (apenas no Cliente)
        if (this.level().isClientSide() && this.expansionTick > 0) {

            // Calcula o raio atual: quanto menor o expansionTick, maior o raio
            // Começa em 0.5 e vai até ~3.0 blocos
            double currentRadius = (MAX_EXPANSION_TICKS - expansionTick) * 0.4D + 0.5D;

            int numParticles = 10;
            for (int i = 0; i < numParticles; i++) {
                double angle = i * (1.0D * Math.PI) / numParticles;
                double offsetX = Math.cos(angle) * currentRadius;
                double offsetZ = Math.sin(angle) * currentRadius;

                // Criamos a partícula na borda do raio atual
                this.level().addParticle(ParticleTypes.FLAME,
                        this.getX() + offsetX, this.getY() + 0.1D, this.getZ() + offsetZ,
                        0, 0, 0); // Velocidade zero para ela ficar no sítio enquanto a próxima nasce mais fora
            }

            this.expansionTick--;
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        // Podes ajustar a força do salto aqui se quiseres saltos maiores
        // Vec3 vec3 = this.getDeltaMovement();
        // this.setDeltaMovement(vec3.x, 0.42D, vec3.z);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    public void setAttackingFire(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttackingFire() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        // Se aterrou e está em modo de ataque
        if (onGround && this.isAttackingFire()) {
            this.spawnFireImpactEffects();
        }
        // IMPORTANTE: Manter o super no fim para ele tratar do dano de queda normal
        super.checkFallDamage(y, onGround, state, pos);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 60) {
            // Reinicia o contador de animação no cliente
            this.expansionTick = MAX_EXPANSION_TICKS;
        } else {
            super.handleEntityEvent(id);
        }
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

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.goalSelector.addGoal(3, new RabbitHopGoal(this));
        this.goalSelector.addGoal(2, new RabbitFireOrbitGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        addBehaviourGoals();

    }

    protected void addBehaviourGoals() {

        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    private void spawnFireImpactEffects() {
        Level level = this.level();

        if (!level.isClientSide) {
            // --- LÓGICA DO SERVIDOR ---
            float damageRadius = 2.0F; // 7.0F era demasiado grande, ia acertar em tudo!
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(damageRadius));

            for (LivingEntity entity : entities) {
                if (entity != this && !(entity instanceof FireRabbitEntity)) {
                    entity.hurt(this.damageSources().onFire(), 2.0F);
                    entity.setSecondsOnFire(3);
                }
            }

            // Envia um sinal para o Cliente mostrar as partículas
            level.broadcastEntityEvent(this, (byte) 60);

            // Som (corre no servidor e sincroniza sozinho)
            level.playSound(null, this.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 1.0F, 1.5F);
        }
    }
}
