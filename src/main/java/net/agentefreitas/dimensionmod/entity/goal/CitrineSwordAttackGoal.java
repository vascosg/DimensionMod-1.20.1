package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.CitrineSwordEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CitrineSwordAttackGoal extends MeleeAttackGoal {

    private final CitrineSwordEntity entity;
    private int attackDelay = 35;
    private int ticksUntilNextAttack = 10;
    private boolean shouldCountTillNextAttack = false;

    public CitrineSwordAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((CitrineSwordEntity) pMob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 35;
        ticksUntilNextAttack = 80;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) return;

        // 1. Deixamos o Minecraft fazer a lógica normal de perseguição primeiro
        super.tick();

        // 2. Agora nós interferimos: Se ele estiver dentro do teu alcance (10.0)
        double distSqr = this.mob.distanceToSqr(target);
        if (distSqr <= this.getAttackReachSqr(target)) {
            // Forçamos o mob a parar de andar para ele não "colar" no player
            this.mob.getNavigation().stop();
        }

        this.checkAndPerformAttack(target, distSqr);
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(50); //??
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {

        return 16.0D;

    }


    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        if (this.mob.doHurtTarget(pEnemy)) { // Se o ataque acertar
            // Calculamos a direção (do mob para o inimigo)
            double d0 = this.mob.getX() - pEnemy.getX();
            double d1 = this.mob.getZ() - pEnemy.getZ();

            // Aplicamos um impulso manual (o 1.5 aqui é a força, podes aumentar)
            pEnemy.knockback(3.5D, d0, d1);

            // Se quiseres que ele suba um pouco (efeito de lançamento)
            pEnemy.setDeltaMovement(pEnemy.getDeltaMovement().add(0, 1.0D, 0));
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
