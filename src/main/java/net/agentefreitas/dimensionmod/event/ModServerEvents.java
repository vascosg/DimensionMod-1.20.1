package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.worldgen.StructureSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModServerEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        Player player = event.player;

        if (player.getMainHandItem().getItem() == ModItems.MOON_BOW.get()) {
            int lightLevel = player.level().getMaxLocalRawBrightness(player.blockPosition());

            if (lightLevel < 7) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            }
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();

        // Usa o Level.RESOURCE_KEY, não Registries.DIMENSION
        ResourceKey<Level> AQUA_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "aqua_dimension"));

        ServerLevel customLevel = server.getLevel(AQUA_DIMENSION);


        if (customLevel != null) {
            ResourceLocation bridgeStartId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_start");
            ResourceLocation bridgeMiddleId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_middle");
            ResourceLocation centerBaseId = new ResourceLocation( "dimensionmod", "aqua_ruin/aqua_center_base");
            ResourceLocation centerTopId = new ResourceLocation( "dimensionmod", "aqua_ruin/aqua_center_top");

            BlockPos pos = new BlockPos(0, 0, 0);
            StructureSpawner.generate(customLevel, pos,bridgeStartId);

            for(int i = 1; i < 20; i++) {
                pos = new BlockPos((48*i),-1,0);
                StructureSpawner.generate(customLevel, pos,bridgeMiddleId);
                if (i == 19 ) {
                    pos = new BlockPos((48*(i+1)),0,-10);
                    StructureSpawner.generate(customLevel, pos,centerBaseId);
                    pos = new BlockPos((48*(i+1)),48,-10);
                    StructureSpawner.generate(customLevel, pos,centerTopId);
                }
            }


        } else {
            System.out.println("❌ Dimensão não encontrada: " + AQUA_DIMENSION.location());
        }


    }
}