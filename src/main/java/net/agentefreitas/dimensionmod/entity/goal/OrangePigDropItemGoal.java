package net.agentefreitas.dimensionmod.entity.goal;

import net.agentefreitas.dimensionmod.entity.custom.OrangePigEntity;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import java.util.EnumSet;

public class OrangePigDropItemGoal extends Goal {
    private final OrangePigEntity mob;
    private Player targetPlayer;
    private int dropDelay = 100; // 5 segundos entre drops (20 ticks = 1s)

    public OrangePigDropItemGoal(OrangePigEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Só ativa se for a variante DROPPER
        if (this.mob.getVariant() != OrangePigEntity.DROPPER_VARIANT) return false;

        // Procura o jogador mais próximo num raio de 10 blocos
        this.targetPlayer = this.mob.level().getNearestPlayer(this.mob, 10.0D);
        return this.targetPlayer != null;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.targetPlayer, 30.0F, 30.0F);
        double distSqr = this.mob.distanceToSqr(this.targetPlayer);

        // Configurações de Distância (em blocos quadrados)
        double distMinimaSqr = 25.0D; // 5 blocos (Foge se estiver mais perto que isto)
        double distMaximaSqr = 64.0D; // 8 blocos (Persegue se estiver mais longe que isto)

        if (distSqr < distMinimaSqr) {
            // --- ESTADO 1: FUGIR ---
            // Calcula a direção oposta ao jogador
            double dX = this.mob.getX() - this.targetPlayer.getX();
            double dZ = this.mob.getZ() - this.targetPlayer.getZ();

            // Move-se para uma posição longe do jogador
            this.mob.getNavigation().moveTo(this.mob.getX() + dX, this.mob.getY(), this.mob.getZ() + dZ, 2.1D);

        } else if (distSqr > distMaximaSqr) {
            // --- ESTADO 2: PERSEGUIR ---
            this.mob.getNavigation().moveTo(this.targetPlayer, 1.2D);

        } else {
            // --- ESTADO 3: ZONA DE CONFORTO ---
            // Está entre 4 e 8 blocos, apenas fica parado a olhar e a dar itens
            this.mob.getNavigation().stop();
        }

        // Lógica do Drop (Mantém-se igual)
        if (--this.dropDelay <= 0) {
            dropItem();
            // Reset do delay (ex: entre 5 e 10 segundos para não floodar o chão)
            this.dropDelay = this.mob.getRandom().nextInt(30);
        }
    }

    private void dropItem() {
        // 1. Definir o item
        ItemStack itemParaLançar = new ItemStack(ModItems.CURSED_PORKCHOP.get());

        // 2. Criar a entidade do item DIRETAMENTE na posição do jogador
        // Adicionamos +2.0D no Y para o item "cair" de cima da cabeça dele
        ItemEntity itemEntity = new ItemEntity(this.mob.level(),
                this.targetPlayer.getX(),
                this.targetPlayer.getY() + 2.0D,
                this.targetPlayer.getZ(),
                itemParaLançar);

        // 3. Configurar movimento (opcional)
        // Definimos o movimento como quase zero (0.0D) para ele cair a direito,
        // ou um valor muito pequeno para ele "flutuar" um pouco.
        itemEntity.setDeltaMovement(0.0D, -0.05D, 0.0D);

        // 4. Configurações extras
        itemEntity.setPickUpDelay(0); // O jogador pode apanhar logo, já que o item é para ele

        // 5. Spawn no mundo
        this.mob.level().addFreshEntity(itemEntity);

        // Feedback visual e sonoro (opcional mas recomendado)
        // Faz o porco olhar e dar um som para o player saber que recebeu algo
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 4); // Partículas de "feliz" se configurado
    }
}