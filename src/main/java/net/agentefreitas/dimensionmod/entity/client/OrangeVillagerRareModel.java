package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerJuiceAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerRareAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerJuiceEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerRareEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class OrangeVillagerRareModel<T extends Entity> extends HierarchicalModel<T> {


    private final ModelPart orange;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart orange_leaf_2;
    private final ModelPart orange_leaf_1;
    private final ModelPart face;
    private final ModelPart eyes;
    private final ModelPart inner_skin;
    private final ModelPart outside;
    private final ModelPart back;
    private final ModelPart bone12;
    private final ModelPart bone11;
    private final ModelPart right;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart left;

    public OrangeVillagerRareModel(ModelPart root) {
        this.orange = root.getChild("orange");
        this.head = this.orange.getChild("head");
        this.tail = this.head.getChild("tail");
        this.orange_leaf_2 = this.tail.getChild("orange_leaf_2");
        this.orange_leaf_1 = this.tail.getChild("orange_leaf_1");
        this.face = this.head.getChild("face");
        this.eyes = this.face.getChild("eyes");
        this.inner_skin = this.face.getChild("inner_skin");
        this.outside = this.head.getChild("outside");
        this.back = this.outside.getChild("back");
        this.bone12 = this.back.getChild("bone12");
        this.bone11 = this.back.getChild("bone11");
        this.right = this.outside.getChild("right");
        this.bottom = this.outside.getChild("bottom");
        this.top = this.outside.getChild("top");
        this.left = this.outside.getChild("left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition orange = partdefinition.addOrReplaceChild("orange", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = orange.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = head.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition orange_leaf_2 = tail.addOrReplaceChild("orange_leaf_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 11.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r1 = orange_leaf_2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 34).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0132F, -0.0344F, 1.2932F));

        PartDefinition orange_leaf_1 = tail.addOrReplaceChild("orange_leaf_1", CubeListBuilder.create().texOffs(34, 30).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 10.0F));

        PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = face.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 2).addBox(3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, 0.0F));

        PartDefinition inner_skin = face.addOrReplaceChild("inner_skin", CubeListBuilder.create().texOffs(18, 30).addBox(-4.0F, -7.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

        PartDefinition outside = head.addOrReplaceChild("outside", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back = outside.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, -7.0F, 6.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.0F));

        PartDefinition bone12 = back.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(-1.0F, -8.0F, 3.0F));

        PartDefinition bone11 = back.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(-1.0F, -9.0F, 3.0F));

        PartDefinition right = outside.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -1.0F, 0.0F));

        PartDefinition bottom = outside.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.0F, 0.0F));

        PartDefinition cube_r2 = bottom.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 0).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition top = outside.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, 0.0F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 15).addBox(-3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition left = outside.addOrReplaceChild("left", CubeListBuilder.create().texOffs(18, 15).addBox(3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(OrangeVillagerRareAnimationDefinitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((OrangeVillagerRareEntity) entity).idleAnimationState, OrangeVillagerRareAnimationDefinitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        orange.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return orange;
    }
}
