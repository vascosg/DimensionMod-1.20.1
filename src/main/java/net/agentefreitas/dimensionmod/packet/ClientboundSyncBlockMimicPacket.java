package net.agentefreitas.dimensionmod.packet;

import net.agentefreitas.dimensionmod.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSyncBlockMimicPacket {
    private final BlockState state;
    private final int duration;

    public ClientboundSyncBlockMimicPacket(BlockState state, int duration) {
        this.state = state;
        this.duration = duration;
    }

    // Decoder
    public ClientboundSyncBlockMimicPacket(FriendlyByteBuf buffer) {
        this.state = NbtUtils.readBlockState(Minecraft.getInstance().level.holderLookup(Registries.BLOCK), buffer.readNbt());
        this.duration = buffer.readInt();
    }

    // Encoder
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(NbtUtils.writeBlockState(this.state));
        buffer.writeInt(this.duration);
    }

    // Handler (Corre no Cliente)
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Aqui estamos no Cliente! Atualizamos a capability do jogador local.
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.getCapability(ModCapabilities.BLOCK_MIMIC_CAP).ifPresent(cap -> {
                    cap.setMimicBlock(state, duration);
                });
            }
        });
        return true;
    }
}