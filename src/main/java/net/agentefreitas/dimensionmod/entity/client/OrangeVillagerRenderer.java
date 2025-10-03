package net.agentefreitas.dimensionmod.entity.client;


import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangeVillagerRenderer extends MobRenderer<OrangeVillagerEntity, OrangeVillagerModel<OrangeVillagerEntity>> {
    public OrangeVillagerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangeVillagerModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_VILLAGER_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeVillagerEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_villager.png");
    }
}
