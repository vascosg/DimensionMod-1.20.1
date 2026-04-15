package net.agentefreitas.dimensionmod.packet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() { return packetId++; }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation("dimensionmod", "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Regista o nosso pacote de Zoom
        net.messageBuilder(SyncFocusPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncFocusPacket::decode)
                .encoder(SyncFocusPacket::encode)
                .consumerMainThread(SyncFocusPacket::handle)
                .add();

        // Registo do teu SustoPacket
        net.messageBuilder(PigPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PigPacket::decode)
                .encoder(PigPacket::encode)
                .consumerMainThread(PigPacket::handle)
                .add();

        INSTANCE.messageBuilder(FlameBubblePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlameBubblePacket::new)
                .encoder(FlameBubblePacket::toBytes)
                .consumerMainThread(FlameBubblePacket::handle)
                .add();

        net.messageBuilder(ClientboundSyncBlockMimicPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSyncBlockMimicPacket::new)
                .encoder(ClientboundSyncBlockMimicPacket::toBytes)
                .consumerMainThread(ClientboundSyncBlockMimicPacket::handle)
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }
}