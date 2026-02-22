package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.AmberSwordEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class AmberKitingAttackGoal extends Goal {
    // Usamos PathfinderMob para ter acesso ao movimento e IA
    private final PathfinderMob mob;
    private final double speedModifier;
    private final int attackInterval;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private LivingEntity target;

    // O construtor agora aceita qualquer PathfinderMob
    public AmberKitingAttackGoal(PathfinderMob mob, double speed, int interval, float radius) {
        this.mob = mob;
        this.speedModifier = speed;
        this.attackInterval = interval;
        this.attackRadiusSqr = radius * radius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();
        return this.target != null && this.target.isAlive();
    }

    @Override
    public void tick() {
        double distToTargetSqr = this.mob.distanceToSqr(this.target);
        boolean canSee = this.mob.getSensing().hasLineOfSight(this.target);

        // 1. LÓGICA DE OLHAR
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // 2. LÓGICA DE MOVIMENTO (Fuga)
        if (distToTargetSqr < 64.0D) { // 8 blocos (8*8)
            Vec3 fleeVec = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.target.position());
            if (fleeVec != null) {
                this.mob.getNavigation().moveTo(fleeVec.x, fleeVec.y, fleeVec.z, this.speedModifier);
            }
        } else if (canSee) {
            this.mob.getNavigation().stop();
        }

        // 3. LÓGICA DE ATAQUE
        if (--this.attackTime == 0) {
            if (!canSee) return;

            // Verificamos se o mob realmente sabe atacar à distância antes de disparar
            if (this.mob instanceof RangedAttackMob rangedMob) {
                float f = (float) Math.sqrt(distToTargetSqr) / (float) Math.sqrt(attackRadiusSqr);
                float f1 = Mth.clamp(f, 0.1F, 1.0F);

                // Dispara o projétil (Amber, Kunzite ou qualquer outro)
                rangedMob.performRangedAttack(this.target, f1);
                this.attackTime = this.attackInterval;
            }
        } else if (this.attackTime < 0) {
            this.attackTime = this.attackInterval;
        }
    }
}
