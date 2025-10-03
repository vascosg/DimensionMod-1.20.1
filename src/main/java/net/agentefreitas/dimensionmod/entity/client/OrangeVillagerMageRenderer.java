package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerJuiceEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerMageEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangeVillagerMageRenderer extends MobRenderer<OrangeVillagerMageEntity, OrangeVillagerMageModel<OrangeVillagerMageEntity>> {

    public OrangeVillagerMageRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangeVillagerMageModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_VILLAGER_MAGE_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeVillagerMageEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_villager_mage.png");
    }

    /*
    @Override
    protected RenderType getRenderType(OrangeVillagerMageEntity entity,
                                       boolean bodyVisible,
                                       boolean translucent,
                                       boolean glowing) {
        // Força a camada translúcida que respeita alpha
        return RenderType.entityTranslucent(getTextureLocation(entity));
    }
    */
}
