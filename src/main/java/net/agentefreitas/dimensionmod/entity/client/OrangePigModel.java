package net.agentefreitas.dimensionmod.entity.client;

import net.agentefreitas.dimensionmod.entity.animations.OrangePigAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.OrangePigEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;


public class OrangePigModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart orange_pig;
    private final ModelPart legs;
    private final ModelPart right;
    private final ModelPart left;
    private final ModelPart body;
    private final ModelPart shield;
    private final ModelPart arms;
    private final ModelPart right2;
    private final ModelPart left2;
    private final ModelPart sword;
    private final ModelPart head;
    private final ModelPart helmet;

    public OrangePigModel(ModelPart root) {
        this.orange_pig = root.getChild("orange_pig");
        this.legs = this.orange_pig.getChild("legs");
        this.right = this.legs.getChild("right");
        this.left = this.legs.getChild("left");
        this.body = this.orange_pig.getChild("body");
        this.shield = this.body.getChild("shield");
        this.arms = this.orange_pig.getChild("arms");
        this.right2 = this.arms.getChild("right2");
        this.left2 = this.arms.getChild("left2");
        this.sword = this.left2.getChild("sword");
        this.head = this.orange_pig.getChild("head");
        this.helmet = this.head.getChild("helmet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition orange_pig = partdefinition.addOrReplaceChild("orange_pig", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legs = orange_pig.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right = legs.addOrReplaceChild("right", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 1.0F));

        PartDefinition left = legs.addOrReplaceChild("left", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 1.0F));

        PartDefinition body = orange_pig.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-4.0F, -24.0F, -1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shield = body.addOrReplaceChild("shield", CubeListBuilder.create().texOffs(48, 0).addBox(-3.0F, -20.0F, 2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 21).addBox(-2.0F, -16.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(19, 42).addBox(-4.0F, -23.0F, 3.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 33).addBox(3.0F, -24.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 36).addBox(0.0F, -24.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 39).addBox(-4.0F, -24.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 42).addBox(-1.0F, -24.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition arms = orange_pig.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right2 = arms.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(24, 25).addBox(-1.0F, -2.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -22.0F, 0.0F));

        PartDefinition left2 = arms.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -22.0F, 1.0F));

        PartDefinition sword = left2.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(32, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 6).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(0.0F, -12.0F, -2.0F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 16).addBox(0.0F, -13.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, -2.0F, 1.3526F, 0.0F, 0.0F));

        PartDefinition head = orange_pig.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(56, 29).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(36, 48).addBox(4.0F, -32.0F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(6, 56).addBox(4.0F, -32.0F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(56, 12).addBox(-5.0F, -32.0F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 48).addBox(-5.0F, -32.0F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.0F, -33.0F, -3.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 23).addBox(-1.0F, -34.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 20).addBox(-1.0F, -35.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 46).addBox(-1.0F, -35.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 51).addBox(-1.0F, -38.0F, 2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 12).addBox(-1.0F, -37.0F, 6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 44).addBox(-1.0F, -37.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 25).addBox(-1.0F, -36.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 46).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 51).addBox(-3.0F, -35.0F, -5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 19).addBox(-3.0F, -35.0F, -4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 46).addBox(-4.0F, -30.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 51).addBox(3.0F, -30.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 53).addBox(4.0F, -29.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 55).addBox(-5.0F, -29.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 56).addBox(-5.0F, -26.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 48).addBox(4.0F, -26.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-4.0F, -32.0F, 5.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(OrangePigAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((OrangePigEntity) entity).idleAnimationState, OrangePigAnimationDefenitions.IDLE, ageInTicks, 1f);
        this.animate(((OrangePigEntity) entity).attackAnimationState, OrangePigAnimationDefenitions.ATTACK, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        orange_pig.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return orange_pig;
    }
}
