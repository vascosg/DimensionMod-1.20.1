package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.BaiYuAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.DiscipleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.BaiYuEntity;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.model.geom.builders.*;

public class BaiYuModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart bai_yu;
	private final ModelPart arms;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart body;
	private final ModelPart wings;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart head;

	public BaiYuModel(ModelPart root) {
		this.bai_yu = root.getChild("bai_yu");
		this.arms = this.bai_yu.getChild("arms");
		this.left_arm = this.arms.getChild("left_arm");
		this.right_arm = this.arms.getChild("right_arm");
		this.legs = this.bai_yu.getChild("legs");
		this.left_leg = this.legs.getChild("left_leg");
		this.right_leg = this.legs.getChild("right_leg");
		this.body = this.bai_yu.getChild("body");
		this.wings = this.body.getChild("wings");
		this.left_wing = this.wings.getChild("left_wing");
		this.right_wing = this.wings.getChild("right_wing");
		this.head = this.bai_yu.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bai_yu = partdefinition.addOrReplaceChild("bai_yu", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arms = bai_yu.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -22.0F, 1.0F));

		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -22.0F, 1.0F));

		PartDefinition legs = bai_yu.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 1.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 1.0F));

		PartDefinition body = bai_yu.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -24.0F, -1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_wing = wings.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(39, 39).addBox(1.0F, -4.0F, 1.0F, 1.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(33, 23).addBox(1.0F, -7.0F, 4.0F, 1.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(36, 26).addBox(1.0F, 7.0F, 3.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(52, 37).addBox(1.0F, -4.0F, 8.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(45, 26).addBox(1.0F, -9.0F, 9.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(38, 42).addBox(1.0F, -8.0F, 6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(55, 23).addBox(1.0F, 9.0F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 26).addBox(1.0F, 9.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 19).addBox(1.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 21).addBox(1.0F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 51).addBox(1.0F, -3.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(55, 11).addBox(1.0F, 4.0F, 8.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(55, 9).addBox(1.0F, 6.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 38).addBox(1.0F, -5.0F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 47).addBox(1.0F, -4.0F, 11.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 19).addBox(1.0F, 1.0F, 8.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 2.0F));

		PartDefinition right_wing = wings.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(39, 39).addBox(-2.0F, -4.0F, 1.0F, 1.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(33, 23).addBox(-2.0F, -7.0F, 4.0F, 1.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(36, 26).addBox(-2.0F, 7.0F, 3.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(52, 37).addBox(-2.0F, -4.0F, 8.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(45, 26).addBox(-2.0F, -9.0F, 9.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(38, 42).addBox(-2.0F, -8.0F, 6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(55, 23).addBox(-2.0F, 9.0F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 26).addBox(-2.0F, 9.0F, 7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 19).addBox(-2.0F, -6.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 21).addBox(-2.0F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 51).addBox(-2.0F, -3.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(55, 11).addBox(-2.0F, 4.0F, 8.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(55, 9).addBox(-2.0F, 6.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 38).addBox(-2.0F, -5.0F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 47).addBox(-2.0F, -4.0F, 11.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 19).addBox(-2.0F, 1.0F, 8.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 2.0F));

		PartDefinition head = bai_yu.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(BaiYuAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((BaiYuEntity) entity).idleAnimationState, BaiYuAnimationDefenitions.IDLE, ageInTicks, 1f);
		this.animate(((BaiYuEntity) entity).attackAnimationState, BaiYuAnimationDefenitions.ATTACK, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bai_yu.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bai_yu;
	}
}