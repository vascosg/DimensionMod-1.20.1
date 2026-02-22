package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import net.minecraft.client.model.HierarchicalModel;

import net.minecraft.world.entity.Entity;

public class AmberProjectileModel <T extends Entity> extends HierarchicalModel<T>{

    private final ModelPart body;
    private final ModelPart dagger;
    private final ModelPart blade;
    private final ModelPart handle;

    public AmberProjectileModel(ModelPart root) {
        this.body = root.getChild("body");
        this.dagger = this.body.getChild("dagger");
        this.blade = this.dagger.getChild("blade");
        this.handle = this.dagger.getChild("handle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition dagger = body.addOrReplaceChild("dagger", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition blade = dagger.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -6.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(0.0F, -7.0F, -3.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition handle = dagger.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(10, 9).addBox(0.0F, -6.0F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(0.0F, -7.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(0.0F, -8.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

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
