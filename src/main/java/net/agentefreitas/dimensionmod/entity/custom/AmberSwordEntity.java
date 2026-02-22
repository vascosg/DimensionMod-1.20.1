package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.goal.AmberKitingAttackGoal;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AmberSwordEntity extends PathfinderMob implements RangedAttackMob {
    public AmberSwordEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }
     **/

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new AmberKitingAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        addBehaviourGoals();

    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        AmberProjectileEntity projectile = new AmberProjectileEntity(this.level(), this);

        Vec3 look = this.getLookAngle();
        double spawnX = this.getX() + look.x * 0.8D;
        double spawnY = this.getEyeY() - 0.2D;
        double spawnZ = this.getZ() + look.z * 0.8D;

        projectile.setPos(spawnX, spawnY, spawnZ);

        double d0 = pTarget.getX() - spawnX;
        double d1 = pTarget.getY(0.3D) - spawnY;
        double d2 = pTarget.getZ() - spawnZ;

        projectile.shoot(d0, d1, d2, 1.6F, 0.0F);

        this.level().addFreshEntity(projectile);
    }
}
