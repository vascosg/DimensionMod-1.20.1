package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.OrangeZordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeZordFactoryAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordFactoryEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class OrangeZordFactoryModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bone;
    private final ModelPart box;
    private final ModelPart mold;
    private final ModelPart doors;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart outside;
    private final ModelPart mold_doors;
    private final ModelPart key;

    public OrangeZordFactoryModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.box = this.bone.getChild("box");
        this.mold = this.box.getChild("mold");
        this.doors = this.box.getChild("doors");
        this.left = this.doors.getChild("left");
        this.right = this.doors.getChild("right");
        this.outside = this.box.getChild("outside");
        this.mold_doors = this.box.getChild("mold_doors");
        this.key = this.bone.getChild("key");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition box = bone.addOrReplaceChild("box", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mold = box.addOrReplaceChild("mold", CubeListBuilder.create().texOffs(-2, 1).addBox(-4.0F, -13.0F, -6.0F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 89).addBox(-4.0F, -5.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 49).addBox(-4.0F, -13.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 37).addBox(2.0F, -13.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 3).addBox(2.0F, -5.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 20).addBox(-2.0F, -4.0F, -7.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(86, 62).addBox(-1.0F, -13.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition doors = box.addOrReplaceChild("doors", CubeListBuilder.create().texOffs(52, 30).addBox(-5.0F, -14.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left = doors.addOrReplaceChild("left", CubeListBuilder.create().texOffs(88, 0).addBox(-5.0F, 0.0F, -5.0F, 5.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -15.0F, 0.0F));

        PartDefinition right = doors.addOrReplaceChild("right", CubeListBuilder.create().texOffs(90, 71).addBox(0.0F, 0.0F, -5.0F, 5.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -15.0F, 0.0F));

        PartDefinition outside = box.addOrReplaceChild("outside", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-6.0F, -2.0F, -7.0F, 12.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-6.0F, -3.0F, -7.0F, 13.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(97, 47).addBox(-5.0F, -15.0F, -7.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(92, 33).addBox(-6.0F, -14.0F, -7.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 15).addBox(5.0F, -16.0F, -7.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(56, 0).addBox(5.0F, -15.0F, -7.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(64, 41).addBox(5.0F, -14.0F, -7.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(64, 56).addBox(-7.0F, -16.0F, -7.0F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(86, 15).addBox(-6.0F, -15.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(60, 86).addBox(-6.0F, -14.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(96, 53).addBox(-5.0F, -16.0F, -7.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 50).addBox(-5.0F, -16.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(89, 11).addBox(-5.0F, -15.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(93, 30).addBox(-5.0F, -14.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 99).addBox(-7.0F, -15.0F, 5.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 82).addBox(-7.0F, -15.0F, -3.0F, 1.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 99).addBox(-7.0F, -15.0F, -7.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 45).addBox(-6.0F, -13.0F, -7.0F, 2.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(4.0F, -13.0F, -7.0F, 2.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(60, 71).addBox(6.0F, -5.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(30, 69).addBox(6.0F, -7.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(30, 84).addBox(6.0F, -9.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 84).addBox(6.0F, -11.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(6.0F, -13.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(60, 69).addBox(6.0F, -12.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 99).addBox(6.0F, -12.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 99).addBox(6.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 99).addBox(6.0F, -8.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 99).addBox(6.0F, -6.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 99).addBox(6.0F, -4.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(58, 41).addBox(6.0F, -2.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 99).addBox(6.0F, -2.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 69).addBox(6.0F, -4.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 67).addBox(6.0F, -6.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 38).addBox(6.0F, -8.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 36).addBox(6.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 36).addBox(-4.0F, -13.0F, 6.0F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mold_doors = box.addOrReplaceChild("mold_doors", CubeListBuilder.create().texOffs(96, 56).addBox(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -6.0F));

        PartDefinition key = bone.addOrReplaceChild("key", CubeListBuilder.create().texOffs(96, 58).addBox(-2.0F, -12.0F, -7.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 99).addBox(2.0F, -11.0F, -7.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 99).addBox(-4.0F, -11.0F, -7.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 99).addBox(-2.0F, -13.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 99).addBox(1.0F, -13.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(((OrangeZordFactoryEntity) entity).openingAnimationState, OrangeZordFactoryAnimationDefenitions.OPENING, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return bone;
    }
}
