package net.agentefreitas.dimensionmod.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlameBubblePacket {

    public FlameBubblePacket() { }

    public FlameBubblePacket(FriendlyByteBuf buffer) { }

    public void toBytes(FriendlyByteBuf buffer) { }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            System.out.println("DEBUG SERVIDOR: Pacote recebido!");

            if (player != null) {
                System.out.println("DEBUG SERVIDOR: Aplicando dano ao player: " + player.getName().getString());
                player.hurt(player.damageSources().onFire(), 2.0F);
                player.setSecondsOnFire(3);
            } else {
                System.out.println("DEBUG SERVIDOR: Player sender é NULO!");
            }
        });
        return true;
    }
}
