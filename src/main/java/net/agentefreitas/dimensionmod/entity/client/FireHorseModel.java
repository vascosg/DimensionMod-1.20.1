package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.FireHorseAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.FireHorseEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FireHorseModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart horse;
    private final ModelPart head;
    private final ModelPart mouth;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart neck;
    private final ModelPart mane;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart front_left_leg;
    private final ModelPart front_right_leg;
    private final ModelPart back_left_leg;
    private final ModelPart back_right_leg;

    public FireHorseModel(ModelPart root) {
        this.horse = root.getChild("horse");
        this.head = this.horse.getChild("head");
        this.mouth = this.horse.getChild("mouth");
        this.left_ear = this.horse.getChild("left_ear");
        this.right_ear = this.horse.getChild("right_ear");
        this.neck = this.horse.getChild("neck");
        this.mane = this.horse.getChild("mane");
        this.body = this.horse.getChild("body");
        this.tail = this.horse.getChild("tail");
        this.front_left_leg = this.horse.getChild("front_left_leg");
        this.front_right_leg = this.horse.getChild("front_right_leg");
        this.back_left_leg = this.horse.getChild("back_left_leg");
        this.back_right_leg = this.horse.getChild("back_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition horse = partdefinition.addOrReplaceChild("horse", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -9.0F));

        PartDefinition head = horse.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 32).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mouth = horse.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(16, 62).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_ear = horse.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 55).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition right_ear = horse.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 51).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition neck = horse.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 32).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mane = horse.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(36, 59).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.01F));

        PartDefinition body = horse.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 9.0F, 15.0F));

        PartDefinition tail = horse.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 44).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 24.0F));

        PartDefinition front_left_leg = horse.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(52, 47).addBox(-3.0F, -1.0F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 12.0F, 0.0F));

        PartDefinition front_right_leg = horse.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(52, 32).addBox(-1.0F, -1.0F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 12.0F, 0.0F));

        PartDefinition back_left_leg = horse.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 51).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 12.0F, 17.0F));

        PartDefinition back_right_leg = horse.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(36, 44).addBox(-1.0F, -1.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 12.0F, 17.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(FireHorseAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FireHorseEntity) entity).idleAnimationState, FireHorseAnimationDefenitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.mouth.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.mouth.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.left_ear.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.left_ear.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.right_ear.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.right_ear.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        horse.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return horse;
    }
}
