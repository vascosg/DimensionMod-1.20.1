package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.FireCowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class CowRamAttackGoal extends Goal {
    private final FireCowEntity entity;
    private LivingEntity target;
    private Vec3 chargeDirection;
    private int attackTimer = 0;
    private static int cooldownGlobal = 0;
    private boolean hasHit = false;

    public CowRamAttackGoal(FireCowEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }



    @Override
    public boolean canUse() {
        if (cooldownGlobal > 0) {
            cooldownGlobal--;
            return false;
        }
        this.target = this.entity.getTarget();
        return target != null && target.isAlive() && this.entity.distanceToSqr(target) < 144.0D;
    }

    @Override
    public void start() {
        this.attackTimer = 0;
        this.hasHit = false;
        this.entity.setAttacking(true);

        // 1. Calcular a direção
        Vec3 targetPos = this.target.position();
        Vec3 entityPos = this.entity.position();
        this.chargeDirection = targetPos.subtract(entityPos).normalize();

        // 2. FORÇAR ROTAÇÃO INSTANTÂNEA
        // Calculamos o ângulo (yaw) baseado na direção do vetor
        float yaw = (float) (Math.atan2(chargeDirection.z, chargeDirection.x) * (180 / Math.PI)) - 90.0F;
        this.entity.setYRot(yaw);
        this.entity.yRotO = yaw;
        this.entity.setYHeadRot(yaw);
        this.entity.yBodyRot = yaw;

        System.out.println("DEBUG COW: CARGA INICIADA (Rotação Corrigida)!");
    }

    @Override
    public void tick() {
        this.attackTimer++;

        if (this.target != null) {
            this.entity.getLookControl().setLookAt(this.target, 90.0F, 90.0F);
        }

        // 1. MOVIMENTO (Impulso inicial)
        if (this.attackTimer < 22) { // Reduzi para 22 para ser mais preciso
            this.entity.setDeltaMovement(this.chargeDirection.x * 0.5D, this.entity.getDeltaMovement().y, this.chargeDirection.z * 0.5D);
        }
        // 2. O TRAVÃO (Fricção forçada após o impulso ou após o hit)
        else {
            Vec3 currentVel = this.entity.getDeltaMovement();
            // Multiplicar por 0.5 a cada tick faz a velocidade desaparecer em menos de 1 segundo
            this.entity.setDeltaMovement(currentVel.x * 0.5D, currentVel.y, currentVel.z * 0.5D);
        }

        // 3. COLISÃO
        if (!hasHit && this.target != null) {
            if (this.entity.distanceTo(this.target) < 2.2F) {
                System.out.println("DEBUG COW: IMPACTO REALIZADO!");
                launchPlayer(this.target);
                this.hasHit = true;

                // Opcional: Se quiseres que ela pare NO MOMENTO que bate,
                // podes até zerar logo o movimento aqui:
                // this.entity.setDeltaMovement(0, this.entity.getDeltaMovement().y, 0);
            }
        }
    }

    @Override
    public boolean canContinueToUse() {

        if (this.hasHit && this.attackTimer > 30) {
            return false;
        }
        return this.attackTimer < 53 && this.target != null && this.target.isAlive();
    }

    @Override
    public void stop() {
        this.entity.setAttacking(false);
        cooldownGlobal = 10;
        System.out.println("DEBUG COW: Goal Finalizado.");
    }


    private void launchPlayer(LivingEntity victim) {
        victim.hurt(this.entity.damageSources().mobAttack(this.entity), 8.0F);
        // Arremesso usando a direção da carga para garantir que ele voa para longe da vaca
        victim.setDeltaMovement(this.chargeDirection.x * 1.2D, 1.1D, this.chargeDirection.z * 1.2D);
        victim.setSecondsOnFire(5);
        victim.hurtMarked = true;
        this.entity.level().broadcastEntityEvent(this.entity, (byte) 4);
    }
}