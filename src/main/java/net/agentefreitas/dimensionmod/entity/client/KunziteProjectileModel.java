package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class KunziteProjectileModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart wave;

    public KunziteProjectileModel(ModelPart root) {
        this.body = root.getChild("body");
        this.wave = this.body.getChild("wave");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition wave = body.addOrReplaceChild("wave", CubeListBuilder.create().texOffs(0, 3).addBox(-4.0F, -6.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -6.0F, 1.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-7.0F, -6.0F, 3.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(3.0F, -6.0F, 3.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(5.0F, -6.0F, 5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-7.0F, -6.0F, 5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 10).addBox(2.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 10).addBox(-3.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-5.0F, -6.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(4.0F, -6.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 6).addBox(6.0F, -6.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-7.0F, -6.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return body;
    }
}
