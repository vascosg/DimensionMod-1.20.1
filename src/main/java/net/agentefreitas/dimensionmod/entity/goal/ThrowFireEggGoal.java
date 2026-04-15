package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.FireChickenEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireEggProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ThrowFireEggGoal extends Goal {
    private final FireChickenEntity mob;
    private LivingEntity target;
    private int attackTimer = 0;
    private static int cooldownGlobal = 0;
    private boolean hasFired = false;

    public ThrowFireEggGoal(FireChickenEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldownGlobal > 0) {
            cooldownGlobal--;
            return false;
        }
        this.target = this.mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        this.attackTimer = 0;
        this.hasFired = false;
        // Começamos a animação imediatamente, pois ela demora 40 ticks a chegar ao "clímax"
        this.mob.setAttacking(true);
    }

    @Override
    public void tick() {
        if (this.target == null) return;

        double distSq = this.mob.distanceToSqr(this.target);
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // 1. MOVIMENTO (Fuga/Aproximação)
        this.handleMovement(distSq);

        // 2. LÓGICA DE ATAQUE
        this.attackTimer++;

        // DISPARO SINCRONIZADO: O ovo só sai quando a animação atinge o ponto certo (40)
        if (!hasFired && this.attackTimer >= 25) {
            this.throwProjectile();
            this.hasFired = true;
        }

        // FIM DO CICLO: Esperamos a animação completar os 53 ticks totais
        if (this.attackTimer >= 40) {
            this.resetAttackCycle();
        }
    }

    private void handleMovement(double distSq) {
        if (distSq < 25.0D) { // Fuga
            Vec3 fleeDir = this.mob.position().subtract(this.target.position()).normalize();
            Vec3 fleePos = this.mob.position().add(fleeDir.scale(4.0D));
            this.mob.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.25D);
        } else if (distSq > 100.0D) { // Perseguição
            this.mob.getNavigation().moveTo(this.target, 1.0D);
        } else {
            this.mob.getNavigation().stop();
        }
    }

    private void resetAttackCycle() {
        this.attackTimer = 0;
        this.hasFired = false;
        // Desligamos e ligamos o setAttacking para resetar a animação no cliente para o próximo tiro
        this.mob.setAttacking(false);
        this.mob.setAttacking(true);
    }

    private void throwProjectile() {
        FireEggProjectileEntity projectile = new FireEggProjectileEntity(this.mob.level(), this.mob);
        double d1 = target.getX() - mob.getX();
        double d2 = (target.getEyeY() - 1.1D) - projectile.getY();
        double d3 = target.getZ() - mob.getZ();

        projectile.shoot(d1, d2 + Math.sqrt(d1 * d1 + d3 * d3) * 0.2D, d3, 1.6F, 1.0F);
        this.mob.level().addFreshEntity(projectile);

        this.mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 1.0F, 1.2F);
    }

    @Override
    public void stop() {
        this.mob.setAttacking(false);
        cooldownGlobal = 40;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && this.mob.distanceToSqr(target) < 400.0D;
    }
}