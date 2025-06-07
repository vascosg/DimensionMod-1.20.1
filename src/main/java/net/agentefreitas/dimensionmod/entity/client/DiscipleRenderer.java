package net.agentefreitas.dimensionmod.entity.client;

import com.google.common.collect.Maps;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.DiscipleVariant;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class DiscipleRenderer extends MobRenderer<DiscipleEntity, DiscipleModel<DiscipleEntity>> {
    private static final Map<DiscipleVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DiscipleVariant.class), map -> {
                map.put(DiscipleVariant.MAN,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/huohui_disciple_man.png"));
                map.put(DiscipleVariant.WOMAN,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/huohui_disciple_woman.png"));
            });

    public DiscipleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DiscipleModel<>(pContext.bakeLayer(ModModelLayers.DISCIPLE_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(DiscipleEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }

    /**
    @Override
    public void render(RhinoEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
    **/
}
