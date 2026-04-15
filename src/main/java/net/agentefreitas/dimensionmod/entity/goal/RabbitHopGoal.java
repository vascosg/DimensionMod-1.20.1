package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.FireRabbitEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class RabbitHopGoal extends Goal {
    private final FireRabbitEntity rabbit;

    public RabbitHopGoal(FireRabbitEntity rabbit) {
        this.rabbit = rabbit;
        // Importante: Não bloqueamos a Flag MOVE se quisermos que a navegação padrão ajude,
        // mas aqui vamos usar JUMP para o impulso.
        this.setFlags(EnumSet.of(Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        // Só salta se tiver um destino e estiver no chão
        return !this.rabbit.getNavigation().isDone() && this.rabbit.onGround();
    }

    @Override
    public void tick() {
        if (this.rabbit.onGround()) {
            // 1. Faz o mob olhar para onde quer ir
            // O getNavigation().getPath() dá-nos o próximo ponto do caminho
            this.rabbit.getJumpControl().jump();

            // 2. Forçamos o movimento para a frente
            // O valor 1.2F é o multiplicador de velocidade do salto
            this.rabbit.setZza(1.2F);
        } else {
            // Enquanto estiver no ar, mantemos o balanço para a frente
            this.rabbit.setZza(1.0F);
        }
    }

    @Override
    public void stop() {
        // Quando parar de saltar, reseta o movimento para a frente
        this.rabbit.setZza(0.0F);
    }
}