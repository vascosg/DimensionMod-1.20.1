package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.FireRabbitEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class RabbitFireOrbitGoal extends Goal {
    private final FireRabbitEntity rabbit;
    private LivingEntity target;
    private double angle = 0;

    public RabbitFireOrbitGoal(FireRabbitEntity rabbit) {
        this.rabbit = rabbit;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.rabbit.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        this.rabbit.setAttackingFire(true);
        // Inicializa o ângulo baseado na posição atual para evitar que ele dê um "teletransporte" mental
        this.angle = Math.atan2(rabbit.getZ() - target.getZ(), rabbit.getX() - target.getX());
    }

    @Override
    public void stop() {
        this.rabbit.setAttackingFire(false);
        this.rabbit.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (target == null) return;

        double radius = 5.0D;
        this.rabbit.getLookControl().setLookAt(target, 30.0F, 30.0F);

        // 1. Ângulo mais rápido
        this.angle += 0.25;

        // 2. Cálculo do ponto alvo
        double targetX = target.getX() + Math.cos(angle) * radius;
        double targetZ = target.getZ() + Math.sin(angle) * radius;

        // 3. MOVIMENTO FÍSICO (O Segredo)
        // Em vez de apenas moveTo, calculamos um vetor de força para o ponto alvo
        Vec3 moveVec = new Vec3(targetX - rabbit.getX(), 0, targetZ - rabbit.getZ()).normalize();

        // Multiplicamos a velocidade base por um valor alto
        double speedMultiplier = 0.05D;

        if (!this.rabbit.onGround()) {
            speedMultiplier = 0.08D;
        }

        // Aplicamos o movimento diretamente no DeltaMovement
        this.rabbit.setDeltaMovement(this.rabbit.getDeltaMovement().add(moveVec.x * speedMultiplier, 0, moveVec.z * speedMultiplier));

        // 4. Salto constante para não prender no chão
        if (this.rabbit.onGround()) {
            this.rabbit.getJumpControl().jump();
            // Som de "dash" opcional para parecer mais potente
        }

        // 5. Mantém a navegação ativa apenas para ele não tentar atravessar paredes
        this.rabbit.getNavigation().moveTo(targetX, target.getY(), targetZ, 1.4D);
    }
}