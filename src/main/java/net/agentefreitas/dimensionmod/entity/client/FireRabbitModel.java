package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.FireRabbitAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.FireRabbitEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FireRabbitModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart rabbit;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_thigh;
    private final ModelPart right_thigh;
    private final ModelPart left_foot;
    private final ModelPart right_foot;
    private final ModelPart tail;

    public FireRabbitModel(ModelPart root) {
        this.rabbit = root.getChild("rabbit");
        this.head = this.rabbit.getChild("head");
        this.nose = this.rabbit.getChild("nose");
        this.left_ear = this.rabbit.getChild("left_ear");
        this.right_ear = this.rabbit.getChild("right_ear");
        this.body = this.rabbit.getChild("body");
        this.left_arm = this.rabbit.getChild("left_arm");
        this.right_arm = this.rabbit.getChild("right_arm");
        this.left_thigh = this.rabbit.getChild("left_thigh");
        this.right_thigh = this.rabbit.getChild("right_thigh");
        this.left_foot = this.rabbit.getChild("left_foot");
        this.right_foot = this.rabbit.getChild("right_foot");
        this.tail = this.rabbit.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rabbit = partdefinition.addOrReplaceChild("rabbit", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, -3.0F));

        PartDefinition head = rabbit.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition nose = rabbit.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(6, 33).addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_ear = rabbit.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(14, 24).addBox(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_ear = rabbit.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 33).addBox(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = rabbit.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 10.0F));

        PartDefinition left_arm = rabbit.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.5F, 0.0F));

        PartDefinition right_arm = rabbit.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.5F, 0.0F));

        PartDefinition left_thigh = rabbit.addOrReplaceChild("left_thigh", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -0.5F, 5.5F));

        PartDefinition right_thigh = rabbit.addOrReplaceChild("right_thigh", CubeListBuilder.create().texOffs(14, 31).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -0.5F, 5.5F));

        PartDefinition left_foot = rabbit.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(20, 15).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, 7.2F));

        PartDefinition right_foot = rabbit.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(20, 23).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, 7.2F));

        PartDefinition tail = rabbit.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 9).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.75F, 9.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(FireRabbitAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FireRabbitEntity) entity).idleAnimationState, FireRabbitAnimationDefenitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.right_ear.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.right_ear.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.left_ear.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.left_ear.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.nose.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.nose.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rabbit.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return rabbit;
    }
}
