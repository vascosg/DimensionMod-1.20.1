package net.agentefreitas.dimensionmod.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.DemiCatAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.OrangeVillagerAnimationDefenitions;
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

public class OrangeVillagerModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructorprivate final ModelPart orange;
	private final ModelPart orange;
	private final ModelPart head;
	private final ModelPart face;
	private final ModelPart inner_skin;
	private final ModelPart eyes;
	private final ModelPart tail;
	private final ModelPart bone10;
	private final ModelPart bone15;
	private final ModelPart outside;
	private final ModelPart back;
	private final ModelPart left;
	private final ModelPart bottom;
	private final ModelPart top;
	private final ModelPart right;

	public OrangeVillagerModel(ModelPart root) {
		this.orange = root.getChild("orange");
		this.head = this.orange.getChild("head");
		this.face = this.head.getChild("face");
		this.inner_skin = this.face.getChild("inner_skin");
		this.eyes = this.face.getChild("eyes");
		this.tail = this.head.getChild("tail");
		this.bone10 = this.tail.getChild("bone10");
		this.bone15 = this.tail.getChild("bone15");
		this.outside = this.head.getChild("outside");
		this.back = this.outside.getChild("back");
		this.left = this.outside.getChild("left");
		this.bottom = this.outside.getChild("bottom");
		this.top = this.outside.getChild("top");
		this.right = this.outside.getChild("right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition orange = partdefinition.addOrReplaceChild("orange", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition head = orange.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition inner_skin = face.addOrReplaceChild("inner_skin", CubeListBuilder.create().texOffs(18, 30).addBox(-4.0F, -7.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition eyes = face.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(36, 2).addBox(3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, 0.0F));

		PartDefinition tail = head.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone10 = tail.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(34, 30).addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 10.0F));

		PartDefinition bone15 = tail.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 11.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition cube_r1 = bone15.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 34).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0132F, -0.0344F, 1.2932F));

		PartDefinition outside = head.addOrReplaceChild("outside", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition back = outside.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, -7.0F, 6.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.0F));

		PartDefinition left = outside.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -1.0F, 0.0F));

		PartDefinition bottom = outside.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.0F, 0.0F));

		PartDefinition cube_r2 = bottom.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 0).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition top = outside.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, 0.0F));

		PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 15).addBox(-3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition right = outside.addOrReplaceChild("right", CubeListBuilder.create().texOffs(18, 15).addBox(3.0F, -7.0F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(OrangeVillagerAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((OrangeVillagerEntity) entity).idleAnimationState, OrangeVillagerAnimationDefenitions.IDLE, ageInTicks, 1f);
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