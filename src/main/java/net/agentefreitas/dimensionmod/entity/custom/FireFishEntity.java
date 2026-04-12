package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.goal.BubbleAttackGoal;
import net.agentefreitas.dimensionmod.particle.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class FireFishEntity extends PathfinderMob {

    public FireFishEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.moveControl = new FlyingMoveControl(this, 30, true);
        this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(FireFishEntity.class, EntityDataSerializers.BOOLEAN);

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
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void startAttack(int type) {
        this.entityData.set(ATTACKING, true);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
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

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 58; // Length in ticks of your animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }

    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(false);
        return flyingpathnavigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new BubbleAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
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
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        // Debug para ver se o pacote de rede chegou ao computador do jogador
        if (this.level().isClientSide()) {
            System.out.println("DEBUG CLIENTE: Recebi o evento byte: " + pId);
        }

        if (pId == (byte)4) {
            System.out.println("DEBUG CLIENTE: A tentar spawnar partículas...");
            spawnBubbleParticles();
        } else {
            super.handleEntityEvent(pId);
        }
    }

    private void spawnBubbleParticles() {
        // 1. Tenta encontrar o player mais próximo no Cliente para servir de alvo
        Player target = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 10.0D, false);

        double startX = this.getX();
        double startY = this.getEyeY();
        double startZ = this.getZ();

        Vec3 direction;

        if (target != null) {
            // Calcula o vetor da boca do peixe até o centro do player
            direction = new Vec3(target.getX() - startX,
                    target.getY() + (target.getBbHeight() / 2.0F) - startY,
                    target.getZ() - startZ);
        } else {
            // Se não houver player perto, usa a direção do olhar como fallback
            direction = this.getViewVector(1.0F).scale(10.0D);
        }

        // 2. Spawn de mais bolhas (40 unidades)
        int numParticles = 40;
        for (int i = 0; i < numParticles; i++) {
            double ratio = i / (double) numParticles;

            // Adiciona um pequeno "espalhar" aleatório (Inaccuracy)
            // para que as bolhas não fiquem todas numa linha perfeitamente fina
            double spread = 0.15D;
            double offsetX = (this.random.nextDouble() - 0.5D) * spread;
            double offsetY = (this.random.nextDouble() - 0.5D) * spread;
            double offsetZ = (this.random.nextDouble() - 0.5D) * spread;

            this.level().addParticle(ModParticleTypes.FLAME_BUBBLE_PARTICLE.get(),
                    startX + (direction.x * ratio) + offsetX,
                    startY + (direction.y * ratio) + offsetY,
                    startZ + (direction.z * ratio) + offsetZ,
                    direction.x * 0.01D, direction.y * 0.01D, direction.z * 0.01D);
        }
    }
}
