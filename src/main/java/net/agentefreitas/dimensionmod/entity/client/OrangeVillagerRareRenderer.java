package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerRareEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangeVillagerRareRenderer extends MobRenderer<OrangeVillagerRareEntity, OrangeVillagerRareModel<OrangeVillagerRareEntity>> {

    public OrangeVillagerRareRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangeVillagerRareModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_VILLAGER_RARE_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeVillagerRareEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_villager_rare.png");
    }
}
