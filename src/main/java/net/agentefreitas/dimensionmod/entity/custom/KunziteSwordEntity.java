package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.goal.AmberKitingAttackGoal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KunziteSwordEntity extends PathfinderMob implements RangedAttackMob {
    public KunziteSwordEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
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

        for (int i = 0; i < 3; i++) {
            KunziteProjectileEntity projectile = new KunziteProjectileEntity(this.level(), this);

            // 1. POSIÇÃO ALEATÓRIA PERTO DO CORPO
            // (random.nextDouble() - 0.5D) * 2.0D gera um valor entre -1.0 e 1.0
            double offsetX = (this.random.nextDouble() - 0.5D) * 1.5D;
            double offsetY = (this.random.nextDouble() - 0.5D) * 1.0D; // Variação na altura
            double offsetZ = (this.random.nextDouble() - 0.5D) * 1.5D;

            double spawnX = this.getX() + offsetX;
            double spawnY = this.getEyeY() + offsetY;
            double spawnZ = this.getZ() + offsetZ;

            projectile.setPos(spawnX, spawnY, spawnZ);

            // 2. CÁLCULO DA DIREÇÃO
            double d0 = pTarget.getX() - spawnX;
            double d1 = pTarget.getY(0.4D) - spawnY;
            double d2 = pTarget.getZ() - spawnZ;

            // 3. VELOCIDADE LENTA
            // Baixamos o 1.6F para algo como 0.5F ou 0.8F para serem lentos.
            // O 10.0F no final adiciona uma ligeira imprecisão para não irem todos para o mesmo ponto exato.
            projectile.shoot(d0, d1, d2, 0.4F, 10.0F);

            this.level().addFreshEntity(projectile);
        }
    }
}
