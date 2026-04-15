package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.capability.BlockMimicCap;
import net.agentefreitas.dimensionmod.capability.IBlockMimicCap;
import net.agentefreitas.dimensionmod.capability.ModCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID)
public class CapabilityEventHandler {
    private static final ResourceLocation BURIED_CAP = new ResourceLocation(DimensionMod.MOD_ID, "buried_cap");

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(BURIED_CAP, new ICapabilityProvider() {
                private final LazyOptional<IBlockMimicCap> instance = LazyOptional.of(BlockMimicCap::new);

                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
                    // Se a capability pedida for a nossa BLOCK_MIMIC_CAP
                    if (cap == ModCapabilities.BLOCK_MIMIC_CAP) {
                        // Retornamos a instância sem usar o .cast()
                        return instance.cast();
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }

    // Faz o "tick" da capability para diminuir o tempo
    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(ModCapabilities.BLOCK_MIMIC_CAP).ifPresent(IBlockMimicCap::tick);
        }
    }
}