package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.OrangePigEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangePigProjectileEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class OrangePigThrowGoal extends Goal {
    private final OrangePigEntity mob;
    private LivingEntity target;

    private final double speedModifier;
    private final int attackInterval; // Tempo entre tiros
    private int attackTime = -1;
    private int seeTime;

    public OrangePigThrowGoal(OrangePigEntity mob, double speed, int interval) {
        this.mob = mob;
        this.speedModifier = speed;
        this.attackInterval = interval;
        // Permite que o mob se mova e olhe ao mesmo tempo
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();

        if (this.mob.getVariant() == OrangePigEntity.MELEE_VARIANT) {
            return false;
        }

        if (this.mob.getVariant() == OrangePigEntity.DROPPER_VARIANT) {
            return false;
        }

        if (livingentity != null && livingentity.isAlive()) {
            this.target = livingentity;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || !this.mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.setAttacking(false);
    }

    @Override
    public void tick() {
        double distSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean canSee = this.mob.getSensing().hasLineOfSight(this.target);

        // Lógica de Aproximação/Recuo
        if (canSee) {
            this.seeTime++;
        } else {
            this.seeTime = 0;
        }

        // Se estiver muito longe, aproxima-se. Se estiver muito perto, para.
        if (distSqr <= 400.0D && this.seeTime >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // Lógica do Ataque
        if (--this.attackTime == 0) {
            if (!canSee) return;

            // 1. Calcular a diferença de posição real
            double dX = this.target.getX() - this.mob.getX();
            // Usamos o centro do corpo do alvo (getY(0.5)) para garantir que aponta para o peito
            double dY = this.target.getY(0.5D) - this.mob.getY(0.8D);
            double dZ = this.target.getZ() - this.mob.getZ();

            // 2. Calcular a distância horizontal (hipotenusa)
            double dDistance = Math.sqrt(dX * dX + dZ * dZ);

            // 3. Compensação de Gravidade Ajustada
            // Em vez de 0.2, usa 0.11 ou 0.12.
            // Isso compensa a queda do machado sem o mandar para a lua a curta distância.
            double gravityCompensation = dDistance * 0.11D;

            OrangePigProjectileEntity projectile = new OrangePigProjectileEntity(this.mob.level(), this.mob);
            projectile.shoot(dX, dY + gravityCompensation, dZ, 1.5F, 0.0F); // Inaccuracy 0.0F para teste de precisão

            this.mob.level().addFreshEntity(projectile);

            // 3. Finalizar animação e resetar cooldown
            //this.mob.setAttacking(false);
            //this.attackTime = this.attackInterval;

        } else if (this.attackTime < -5) {
            this.mob.setAttacking(false);
            this.attackTime = this.attackInterval;
        }

        // Ativa a animação de THROW (tipo 1) um pouco antes de disparar
        if (this.attackTime == 5) { // 15 ticks antes do disparo real
            this.mob.startAttack(1); // 1 = THROW
        }
    }
}