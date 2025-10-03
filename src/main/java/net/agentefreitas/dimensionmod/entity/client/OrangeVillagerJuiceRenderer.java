package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerJuiceEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrangeVillagerJuiceRenderer extends MobRenderer<OrangeVillagerJuiceEntity, OrangeVillagerJuiceModel<OrangeVillagerJuiceEntity>> {

    public OrangeVillagerJuiceRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,  new OrangeVillagerJuiceModel<>(pContext.bakeLayer(ModModelLayers.ORANGE_VILLAGER_JUICE_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrangeVillagerJuiceEntity pEntity) {
        return new ResourceLocation(DimensionMod.MOD_ID, "textures/entity/orange_villager_juice.png");
    }

    @Override
    protected RenderType getRenderType(OrangeVillagerJuiceEntity entity,
                                       boolean bodyVisible,
                                       boolean translucent,
                                       boolean glowing) {
        // Força a camada translúcida que respeita alpha
        return RenderType.entityTranslucent(getTextureLocation(entity));
    }
}

