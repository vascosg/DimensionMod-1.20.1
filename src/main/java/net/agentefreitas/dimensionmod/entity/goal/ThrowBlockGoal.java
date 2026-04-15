package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.BlockProjectileEntity;
import net.agentefreitas.dimensionmod.entity.custom.FirePigEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class ThrowBlockGoal extends Goal {
    private final FirePigEntity mob;
    private LivingEntity target;
    private int attackTimer = 0;
    private static int cooldownGlobal = 0;

    // Controladores para disparar apenas uma vez em cada tick específico
    private boolean shot1, shot2, shot3;

    public ThrowBlockGoal(FirePigEntity mob) {
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
        return target != null && target.isAlive() && this.mob.distanceToSqr(target) < 256.0D;
    }

    @Override
    public void start() {
        this.attackTimer = 0;
        this.shot1 = false;
        this.shot2 = false;
        this.shot3 = false;
        this.mob.setAttacking(true);
    }

    @Override
    public void tick() {
        if (this.target == null) return;

        this.attackTimer++;
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // Garante que o porco não saia a andar enquanto faz a animação de "metralhadora"
        this.mob.getNavigation().stop();

        // DISPARO 1: Próximo aos 0.5s
        if (this.attackTimer == 10 && !shot1) {
            this.throwBlock();
            this.shot1 = true;
        }

        // DISPARO 2: Meio da janela
        if (this.attackTimer == 22 && !shot2) {
            this.throwBlock();
            this.shot2 = true;
        }

        // DISPARO 3: Próximo aos 1.7s
        if (this.attackTimer == 34 && !shot3) {
            this.throwBlock();
            this.shot3 = true;
        }
    }

    private void throwBlock() {
        if (!this.mob.level().isClientSide) {
            BlockProjectileEntity projectile = new BlockProjectileEntity(this.mob.level(), this.mob);

            double d1 = target.getX() - mob.getX();
            double d2 = (target.getEyeY() - 0.3D) - projectile.getY();
            double d3 = target.getZ() - mob.getZ();

            // Atira com um pouco de imprecisão (1.5F) para os blocos não irem todos para o mesmo pixel
            projectile.shoot(d1, d2, d3, 1.3F, 2.0F);
            this.mob.level().addFreshEntity(projectile);
        }
    }

    @Override
    public boolean canContinueToUse() {
        // Mantém o goal ativo até a animação de 53 ticks acabar
        return this.attackTimer < 53 && this.target != null && this.target.isAlive();
    }

    @Override
    public void stop() {
        this.mob.setAttacking(false);
        cooldownGlobal = 20; // 1 segundos de descanso entre rajadas
    }
}