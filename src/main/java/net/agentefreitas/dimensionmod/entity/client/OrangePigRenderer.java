package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangePigEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerJuiceEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangePigRenderer extends MobRenderer<OrangePigEntity, OrangePigModel<OrangePigEntity>> {

    public OrangePigRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangePigModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_PIG_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangePigEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_pig.png");
    }

    @Override
    protected RenderType getRenderType(OrangePigEntity entity,
                                       boolean bodyVisible,
                                       boolean translucent,
                                       boolean glowing) {
        // Força a camada translúcida que respeita alpha
        return RenderType.entityTranslucent(getTextureLocation(entity));
    }
}
