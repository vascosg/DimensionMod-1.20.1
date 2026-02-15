package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.GarnetSwordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.*;

public class GarnetSwordModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart garnet_sword;
    private final ModelPart handle;
    private final ModelPart blade;

    public GarnetSwordModel(ModelPart root) {
        this.body = root.getChild("body");
        this.garnet_sword = this.body.getChild("garnet_sword");
        this.handle = this.garnet_sword.getChild("handle");
        this.blade = this.garnet_sword.getChild("blade");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition garnet_sword = body.addOrReplaceChild("garnet_sword", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -10.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition handle = garnet_sword.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 13).addBox(1.0F, 4.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 13).addBox(0.0F, 4.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 9).addBox(1.0F, 3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 11).addBox(1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 15).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.694F, 7.7496F, 0.0F));

        PartDefinition blade = garnet_sword.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 4).addBox(-1.0F, -16.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 9).addBox(-2.0F, -17.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 9).addBox(-3.0F, -18.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 7).addBox(-4.0F, -18.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.694F, 7.7496F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(GarnetSwordAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);

        this.animate(((GarnetSwordEntity) entity).idleAnimationState, GarnetSwordAnimationDefenitions.IDLE, ageInTicks, 1f);
        this.animate(((GarnetSwordEntity) entity).attackAnimationState, GarnetSwordAnimationDefenitions.ATTACK, ageInTicks, 1f);
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
