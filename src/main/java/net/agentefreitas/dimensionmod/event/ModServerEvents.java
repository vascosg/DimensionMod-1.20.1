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
            BlockPos pos = new BlockPos(0, 0, 0);
            ResourceLocation bridgeStartId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_start");
            StructureSpawner.generate(customLevel, pos,bridgeStartId);
            BlockPos pos2 = new BlockPos(48, -1, 0);
            ResourceLocation bridgeMiddleId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_middle");
            StructureSpawner.generate(customLevel, pos2,bridgeMiddleId);
            BlockPos pos3 = new BlockPos((48*2), -1, 0);
            BlockPos pos4 = new BlockPos((48*3), -1, 0);
            BlockPos pos5 = new BlockPos((48*4), -1, 0);
            BlockPos pos6 = new BlockPos((48*5), -1, 0);
            BlockPos pos7 = new BlockPos((48*6), -1, 0);
            BlockPos pos8 = new BlockPos((48*7), -1, 0);
            BlockPos pos9 = new BlockPos((48*8), -1, 0);
            BlockPos pos10 = new BlockPos((48*9), -1, 0);
            StructureSpawner.generate(customLevel, pos3,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos4,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos5,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos6,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos7,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos8,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos9,bridgeMiddleId);
            StructureSpawner.generate(customLevel, pos10,bridgeMiddleId);
        } else {
            System.out.println("❌ Dimensão não encontrada: " + AQUA_DIMENSION.location());
        }


    }
}