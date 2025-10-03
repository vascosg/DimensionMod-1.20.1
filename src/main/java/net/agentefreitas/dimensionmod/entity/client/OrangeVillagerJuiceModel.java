package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.DemiCatAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerJuiceAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.DemiCatEntity;
import net.agentefreitas.dimensionmod.entity.custom.OrangeVillagerEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.antlr.v4.runtime.atn.SemanticContext;

public class OrangeVillagerJuiceModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart orange_glass;
    private final ModelPart head;
    private final ModelPart straw;
    private final ModelPart juice;
    private final ModelPart eyes;
    private final ModelPart side_glass;
    private final ModelPart frontal_glass;
    private final ModelPart bottom_glass;

    public OrangeVillagerJuiceModel(ModelPart root) {
        this.orange_glass = root.getChild("orange_glass");
        this.head = this.orange_glass.getChild("head");
        this.straw = this.head.getChild("straw");
        this.juice = this.head.getChild("juice");
        this.eyes = this.head.getChild("eyes");
        this.side_glass = this.head.getChild("side_glass");
        this.frontal_glass = this.head.getChild("frontal_glass");
        this.bottom_glass = this.head.getChild("bottom_glass");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition orange_glass = partdefinition.addOrReplaceChild("orange_glass", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition head = orange_glass.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition straw = head.addOrReplaceChild("straw", CubeListBuilder.create(), PartPose.offset(-1.0F, -3.0F, 0.0F));

        PartDefinition straw_r1 = straw.addOrReplaceChild("straw_r1", CubeListBuilder.create().texOffs(10, 19).addBox(0.0F, -7.0F, -1.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition juice = head.addOrReplaceChild("juice", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.01F, -1.01F, 0.01F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(14, 19).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 8).addBox(3.0F, -2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.0F, 4.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition side_glass = head.addOrReplaceChild("side_glass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_r1 = side_glass.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -7.0F, -3.0F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition left_r1 = side_glass.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -7.0F, -3.0F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition frontal_glass = head.addOrReplaceChild("frontal_glass", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -7.0F, -3.0F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 11).addBox(-2.0F, -7.0F, 2.0F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bottom_glass = head.addOrReplaceChild("bottom_glass", CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(OrangeVillagerJuiceAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        //this.animate(((OrangeVillagerEntity) entity).idleAnimationState, OrangeVillagerAnimationDefenitions.IDLE, ageInTicks, 1f); no anime yet
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = -pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        orange_glass.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return orange_glass;
    }
}
