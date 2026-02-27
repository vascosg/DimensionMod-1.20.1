package net.agentefreitas.dimensionmod;

import com.mojang.logging.LogUtils;
import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.entity.MobEntitySpawnPlacements;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.client.*;
import net.agentefreitas.dimensionmod.event.BlockPlaceRestriction;
import net.agentefreitas.dimensionmod.event.ChatListener;
import net.agentefreitas.dimensionmod.event.NoClouds;
import net.agentefreitas.dimensionmod.event.TeleportToCustomDimension;
import net.agentefreitas.dimensionmod.item.ModCreativeModTabs;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.ModPaintings;
import net.agentefreitas.dimensionmod.util.ModDatapackLoader;
//import net.agentefreitas.dimensionmod.worldgen.dimension.ModDimensions;
import net.agentefreitas.dimensionmod.util.ModItemProperties;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DimensionMod.MOD_ID)
public class DimensionMod {
    public static final String MOD_ID = "dimensionmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static HolderGetter<PlacedFeature> PLACED_FEATURES;
    public static HolderGetter<ConfiguredWorldCarver<?>> CONFIGURED_CARVERS;

    public DimensionMod() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(TeleportToCustomDimension.class);
        MinecraftForge.EVENT_BUS.register(BlockPlaceRestriction.class);
        MinecraftForge.EVENT_BUS.register(NoClouds.class);
        MinecraftForge.EVENT_BUS.register(ChatListener.class);
        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModEnchantments.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(ModDatapackLoader.class);
        //ModDimensions.register();


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // event.enqueueWork(() -> {
        //            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        //        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.DISCIPLE.get(), DiscipleRenderer::new);
            EntityRenderers.register(ModEntities.LITTLE_PURPLE.get(), LittlePurpleRenderer::new);
            EntityRenderers.register(ModEntities.BAI_YU.get(), BaiYuRenderer::new);
            EntityRenderers.register(ModEntities.GAO_YU.get(), GaoYuRenderer::new);
            EntityRenderers.register(ModEntities.DEMI_CAT.get(), DemiCatRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_FRUIT.get(), OrangeFruitRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_VILLAGER.get(), OrangeVillagerRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_VILLAGER_RARE.get(), OrangeVillagerRareRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_VILLAGER_JUICE.get(), OrangeVillagerJuiceRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_VILLAGER_MAGE.get(), OrangeVillagerMageRenderer::new);
            EntityRenderers.register(ModEntities.ORANGE_PIG.get(), OrangePigRenderer::new);
            EntityRenderers.register(ModEntities.GARNET_SWORD.get(), GarnetSwordRenderer::new);
            EntityRenderers.register(ModEntities.AQUAMARINE_SWORD.get(), AquamarineSwordRenderer::new);
            EntityRenderers.register(ModEntities.PERIDOT_SWORD.get(), PeridotSwordRenderer::new);
            EntityRenderers.register(ModEntities.CITRINE_SWORD.get(), CitrineSwordRenderer::new);
            EntityRenderers.register(ModEntities.AMBER_SWORD.get(), AmberSwordRenderer::new);
            EntityRenderers.register(ModEntities.KUNZITE_SWORD.get(), KunziteSwordRenderer::new);
            EntityRenderers.register(ModEntities.AMBER_PROJECTILE.get(), AmberProjectileRenderer::new);
            EntityRenderers.register(ModEntities.KUNZITE_PROJECTILE.get(), KunziteProjectileRenderer::new);
            ModItemProperties.addCustomItemProperties();
        }
    }
}