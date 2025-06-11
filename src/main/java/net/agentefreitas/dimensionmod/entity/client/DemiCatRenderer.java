package net.agentefreitas.dimensionmod.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.DemiCatVariant;
import net.agentefreitas.dimensionmod.entity.DiscipleVariant;
import net.agentefreitas.dimensionmod.entity.custom.BaiYuEntity;
import net.agentefreitas.dimensionmod.entity.custom.DemiCatEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class DemiCatRenderer extends MobRenderer<DemiCatEntity, DemiCatModel<DemiCatEntity>> {

    private static final Map<DemiCatVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DemiCatVariant.class), map -> {
                map.put(DemiCatVariant.MANONE,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/demi_cat_m1.png"));
                map.put(DemiCatVariant.WOMANONE,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/demi_cat_w1.png"));
                map.put(DemiCatVariant.MANTWO,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/demi_cat_m2.png"));
                map.put(DemiCatVariant.WOMANTWO,
                        new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/demi_cat_w2.png"));
            });

    public DemiCatRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DemiCatModel<>(pContext.bakeLayer(ModModelLayers.DEMI_CAT_LAYER)), 1f);
    }


    @Override
    public ResourceLocation getTextureLocation(DemiCatEntity pEntity) {
        return LOCATION_BY_VARIANT.get(pEntity.getVariant());
    }
}
