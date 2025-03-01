package net.agentefreitas.dimensionmod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlaceRestriction {

    final static int CBM = 1000; // The number of times a Common Block had to be Mined to be placed in the dimension
    final static int RBM = 5000; // Rare
    final static int EBM = 7000; // Elite
    private static final ResourceKey<Level> EMPTY_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "empty_dimension"));

    @SubscribeEvent
    public static void onBlockPlace(EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Level level = player.level();
            BlockPos pos = event.getPos();
            int times_mined = player.getStats().getValue(Stats.BLOCK_MINED, event.getPlacedBlock().getBlock());


            if (level.dimension().equals(EMPTY_DIMENSION)) {

                if (times_mined <= CBM) {
                    event.setCanceled(true);
                    player.sendSystemMessage(Component.literal("Mine more of this block to place it!"));
                }
            }
        }
    }
}