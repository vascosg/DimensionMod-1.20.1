package net.agentefreitas.dimensionmod.packet;

import net.agentefreitas.dimensionmod.event.ModClientEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncFocusPacket {
    private final int progress;

    public SyncFocusPacket(int progress) {
        this.progress = progress;
    }

    public static void encode(SyncFocusPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.progress);
    }

    public static SyncFocusPacket decode(FriendlyByteBuf buffer) {
        return new SyncFocusPacket(buffer.readInt());
    }

    public static void handle(SyncFocusPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // No Cliente, guardamos o progresso numa variável global
            ModClientEvents.currentClientFocus = msg.progress;
        });
        context.setPacketHandled(true);
    }
}
