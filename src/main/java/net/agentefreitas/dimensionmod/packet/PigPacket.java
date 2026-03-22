package net.agentefreitas.dimensionmod.packet;

import net.agentefreitas.dimensionmod.client.ClientPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PigPacket {
    public PigPacket() {}

    // Transforma os dados para enviar pela rede
    public PigPacket(FriendlyByteBuf buffer) {}

    public void toBytes(FriendlyByteBuf buffer) {}

    public static PigPacket decode(FriendlyByteBuf buffer) {
        return new PigPacket();
    }

    public static void encode(PigPacket msg, FriendlyByteBuf buffer) {
        // Nada para escrever, o simples recebimento do pacote já ativa o efeito
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // AQUI CHAMAMOS O CLIENTE
            ClientPayloadHandler.handleSusto();
        });
        return true;
    }
}