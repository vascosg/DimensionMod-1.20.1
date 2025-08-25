package net.agentefreitas.dimensionmod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class OrangeFruitEntity extends Entity {

    public OrangeFruitEntity(EntityType<? extends OrangeFruitEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        // Sem dados sincronizados adicionais
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        // Se quiseres salvar dados da fruta (ex: tempo de vida), faz aqui
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Salvar dados personalizados da fruta
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {

            // Gravidade
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04, 0));

            // Movimento
            this.move(MoverType.SELF, this.getDeltaMovement());

            // Colisão com chão: não descarta imediatamente, só ajusta Y
            if (this.onGround()) {
                // diminui velocidade vertical
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.5, 1.0));
            }

            // Colisão com jogador
            List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(0.2));
            for (Player player : players) {
                if (player instanceof ServerPlayer sp) {
                    // Teleporte ou efeito
                    sp.teleportTo(sp.serverLevel(), 0, 100, 0, sp.getYRot(), sp.getXRot());
                    this.discard(); // só descarta depois de tocar no jogador
                }
            }
        }
    }


}