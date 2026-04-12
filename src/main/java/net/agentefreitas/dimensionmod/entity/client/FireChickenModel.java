package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.FireChickenAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.FireChickenEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FireChickenModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart chicken;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart body;
    private final ModelPart chin;
    private final ModelPart bill;
    private final ModelPart head;

    public FireChickenModel(ModelPart root) {
        this.chicken = root.getChild("chicken");
        this.right_leg = this.chicken.getChild("right_leg");
        this.left_leg = this.chicken.getChild("left_leg");
        this.right_wing = this.chicken.getChild("right_wing");
        this.left_wing = this.chicken.getChild("left_wing");
        this.body = this.chicken.getChild("body");
        this.chin = this.chicken.getChild("chin");
        this.bill = this.chicken.getChild("bill");
        this.head = this.chicken.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition chicken = partdefinition.addOrReplaceChild("chicken", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_leg = chicken.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(14, 24).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, 1.0F));

        PartDefinition left_leg = chicken.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -5.0F, 1.0F));

        PartDefinition right_wing = chicken.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(14, 14).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, 0.0F));

        PartDefinition left_wing = chicken.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -11.0F, 0.0F));

        PartDefinition body = chicken.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition chin = chicken.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(26, 24).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -4.0F));

        PartDefinition bill = chicken.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(24, 8).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -4.0F));

        PartDefinition head = chicken.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(FireChickenAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FireChickenEntity) entity).idleAnimationState, FireChickenAnimationDefenitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.chin.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.chin.xRot = pHeadPitch * ((float)Math.PI / 180F);

        this.bill.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.bill.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        chicken.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return chicken;
    }
}
