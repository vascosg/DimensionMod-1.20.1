package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.FireFishEntity;
import net.agentefreitas.dimensionmod.particle.ModParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class BubbleAttackGoal extends Goal {
    private final FireFishEntity entity;
    private int attackTick = 0;

    public BubbleAttackGoal(FireFishEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public void start() {
        super.start();
        System.out.println("DEBUG: Goal de Ataque INICIADO!");
        this.entity.setAttacking(true);
        this.attackTick = 0;
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("DEBUG: Goal de Ataque PARADO!");
        this.entity.setAttacking(false);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        boolean canUse = target instanceof Player && target.isAlive() && this.entity.distanceToSqr(target) < 100.0D;

        // Descomenta a linha abaixo se quiseres ver o estado do target constantemente
        // if (target != null) System.out.println("DEBUG: Distancia para o player: " + this.entity.distanceToSqr(target));

        return canUse;
    }

    @Override
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);

            this.attackTick++;

            // Debug para ver o contador no console
            if (this.attackTick % 10 == 0) {
                System.out.println("DEBUG: Contador de ataque em: " + this.attackTick + "/58");
            }

            if (this.attackTick >= 27) {
                System.out.println("DEBUG: byte enviado");
                this.entity.level().broadcastEntityEvent(this.entity, (byte)4);
                this.attackTick = 0;
            }
        } else {
            System.out.println("DEBUG: Target é nulo no Tick!");
        }
    }
}