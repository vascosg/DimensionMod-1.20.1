package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerJuiceAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerMageAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerMageEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class OrangeVillagerMageModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart orange_mage;
    private final ModelPart head;
    private final ModelPart orange;
    private final ModelPart cajado;
    private final ModelPart mustache;
    private final ModelPart hat;
    private final ModelPart eyes;
    private final ModelPart inner_skin;
    private final ModelPart tail;
    private final ModelPart orange_leaf_2;
    private final ModelPart orange_leaf_1;
    private final ModelPart outside;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart back;
    private final ModelPart bone12;
    private final ModelPart bone11;

    public OrangeVillagerMageModel(ModelPart root) {
        this.orange_mage = root.getChild("orange_mage");
        this.head = this.orange_mage.getChild("head");
        this.orange = this.head.getChild("orange");
        this.cajado = this.head.getChild("cajado");
        this.mustache = this.head.getChild("mustache");
        this.hat = this.head.getChild("hat");
        this.eyes = this.head.getChild("eyes");
        this.inner_skin = this.head.getChild("inner_skin");
        this.tail = this.head.getChild("tail");
        this.orange_leaf_2 = this.tail.getChild("orange_leaf_2");
        this.orange_leaf_1 = this.tail.getChild("orange_leaf_1");
        this.outside = this.head.getChild("outside");
        this.left = this.outside.getChild("left");
        this.right = this.outside.getChild("right");
        this.bottom = this.outside.getChild("bottom");
        this.top = this.outside.getChild("top");
        this.back = this.outside.getChild("back");
        this.bone12 = this.back.getChild("bone12");
        this.bone11 = this.back.getChild("bone11");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition orange_mage = partdefinition.addOrReplaceChild("orange_mage", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = orange_mage.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition orange = head.addOrReplaceChild("orange", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cajado = head.addOrReplaceChild("cajado", CubeListBuilder.create().texOffs(0, 57).addBox(-1.0F, -9.0F, -10.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, 6.0F));

        PartDefinition cube_r1 = cajado.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 50).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -9.0F, -9.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r2 = cajado.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 53).addBox(0.0F, -10.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9163F, 0.0F, 0.0F));

        PartDefinition mustache = head.addOrReplaceChild("mustache", CubeListBuilder.create().texOffs(44, 36).addBox(-2.0F, -3.0F, 0.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 6).addBox(-3.0F, -2.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 6).addBox(0.0F, -2.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -21.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r3 = hat.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 10.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r4 = hat.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 13).addBox(-5.0F, -3.0F, -6.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 6.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r5 = hat.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(44, 22).addBox(-3.0F, -2.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 8.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r6 = hat.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -3.0F, -3.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 5.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r7 = hat.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0F, -2.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(34, 22).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 37).addBox(3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, 0.0F));

        PartDefinition inner_skin = head.addOrReplaceChild("inner_skin", CubeListBuilder.create().texOffs(44, 28).addBox(-4.0F, -7.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

        PartDefinition tail = head.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition orange_leaf_2 = tail.addOrReplaceChild("orange_leaf_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 11.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r8 = orange_leaf_2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(46, 9).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0132F, -0.0344F, 1.2932F));

        PartDefinition orange_leaf_1 = tail.addOrReplaceChild("orange_leaf_1", CubeListBuilder.create().texOffs(18, 33).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 10.0F));

        PartDefinition outside = head.addOrReplaceChild("outside", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left = outside.addOrReplaceChild("left", CubeListBuilder.create().texOffs(26, 24).addBox(3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -1.0F, 0.0F));

        PartDefinition right = outside.addOrReplaceChild("right", CubeListBuilder.create().texOffs(18, 39).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -1.0F, 0.0F));

        PartDefinition bottom = outside.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.0F, 0.0F));

        PartDefinition cube_r9 = bottom.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(36, 39).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition top = outside.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, 0.0F));

        PartDefinition cube_r10 = top.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 33).addBox(-3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition back = outside.addOrReplaceChild("back", CubeListBuilder.create().texOffs(34, 13).addBox(-4.0F, -7.0F, 6.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.0F));

        PartDefinition bone12 = back.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(-1.0F, -8.0F, 3.0F));

        PartDefinition bone11 = back.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(-1.0F, -9.0F, 3.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(OrangeVillagerMageAnimationDefinitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((OrangeVillagerMageEntity) entity).idleAnimationState, OrangeVillagerMageAnimationDefinitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        orange_mage.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return orange_mage;
    }
}
