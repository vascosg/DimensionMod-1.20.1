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
    }
}