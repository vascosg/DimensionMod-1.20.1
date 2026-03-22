package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class OrangePigProjectileModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart sword;

    public OrangePigProjectileModel(ModelPart root) {
        this.sword = root.getChild("sword");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition sword = partdefinition.addOrReplaceChild("sword", CubeListBuilder.create()
                        .texOffs(6, 6).addBox(-1.0F, 3.0157F, -0.0126F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, 2.0157F, -2.0126F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 6).addBox(0.0F, -6.9843F, -1.0126F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 10).addBox(0.0F, -7.9843F, -0.0126F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3526F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        sword.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    @Override
    public ModelPart root() {
        return null;
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
