package net.agentefreitas.dimensionmod.event;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TeleportToCustomDimension {
    private static final ResourceKey<Level> CUSTOM_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            new ResourceLocation("dimensionmod", "empty_dimension")
    );


    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getLevel().isClientSide) {  // Corrigido: getWorld() -> getLevel()
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ServerLevel targetDimension = player.getServer().getLevel(CUSTOM_DIMENSION);

            if (targetDimension != null) {
                player.teleportTo(targetDimension, 0, 110, 0, player.getYRot(), player.getXRot());

                CommandSourceStack source = player.createCommandSourceStack().withPermission(4);
                player.getServer().getCommands().performPrefixedCommand(source, "reload");
            }
        }
    }

}