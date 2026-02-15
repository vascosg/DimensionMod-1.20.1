package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.DISCIPLE_LAYER, DiscipleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LITTLE_PURPLE_LAYER, LittlePurpleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BAI_YU_LAYER, BaiYuModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GAO_YU_LAYER, GaoYuModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DEMI_CAT_LAYER, DemiCatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_FRUIT_LAYER, OrangeFruitModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_VILLAGER_LAYER, OrangeVillagerModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_VILLAGER_RARE_LAYER, OrangeVillagerRareModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_VILLAGER_JUICE_LAYER, OrangeVillagerJuiceModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_VILLAGER_MAGE_LAYER, OrangeVillagerMageModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ORANGE_PIG_LAYER, OrangePigModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GARNET_SWORD_LAYER, GarnetSwordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AQUAMARINE_SWORD_LAYER, AquamarineSwordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PERIDOT_SWORD_LAYER, PeridotSwordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CITRINE_SWORD_LAYER, CitrineSwordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AMBER_SWORD_LAYER, AmberSwordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.KUNZITE_SWORD_LAYER, KunziteSwordModel::createBodyLayer);
    }
}