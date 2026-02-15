package net.agentefreitas.dimensionmod.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.GarnetSwordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.PeridotSwordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.PeridotSwordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PeridotSwordModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart body;
	private final ModelPart peridot_sword;
	private final ModelPart handle;
	private final ModelPart blade;
	private final ModelPart leafs;

	public PeridotSwordModel(ModelPart root) {
		this.body = root.getChild("body");
		this.peridot_sword = this.body.getChild("peridot_sword");
		this.handle = this.peridot_sword.getChild("handle");
		this.blade = this.peridot_sword.getChild("blade");
		this.leafs = this.peridot_sword.getChild("leafs");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition peridot_sword = body.addOrReplaceChild("peridot_sword", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition handle = peridot_sword.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(20, 12).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(-1.0F, -6.0F, -3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(24, 4).addBox(0.0F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 24).addBox(0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 24).addBox(0.0F, -6.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 4).addBox(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 24).addBox(0.0F, -5.0F, -2.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition blade = peridot_sword.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 1.0F, -2.0F, 1.0F, 19.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 16).addBox(0.0F, 5.0F, -4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(0.0F, 5.0F, 1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 30).addBox(0.0F, 13.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 24).addBox(0.0F, 13.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 30).addBox(0.0F, 3.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 28).addBox(0.0F, 3.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition leafs = peridot_sword.addOrReplaceChild("leafs", CubeListBuilder.create().texOffs(24, 0).addBox(2.0F, -7.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(32, 6).addBox(0.0F, -7.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 36).addBox(0.0F, -7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 31).addBox(0.0F, -8.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 31).addBox(0.0F, -8.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 20).addBox(0.0F, -8.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 8).addBox(0.0F, -8.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 31).addBox(0.0F, -10.0F, -7.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(0.0F, -10.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 22).addBox(0.0F, -10.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 12).addBox(0.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 24).addBox(-1.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 10).addBox(-1.0F, -10.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 26).addBox(1.0F, -10.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 36).addBox(1.0F, -10.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 12).addBox(1.0F, -8.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 35).addBox(1.0F, -8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 34).addBox(1.0F, -8.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 0).addBox(1.0F, -8.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 34).addBox(2.0F, -9.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 36).addBox(2.0F, -9.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 34).addBox(2.0F, -9.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 2).addBox(2.0F, -9.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 34).addBox(-2.0F, -9.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 34).addBox(-2.0F, -9.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 18).addBox(-2.0F, -9.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 32).addBox(-2.0F, -9.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 34).addBox(-1.0F, -9.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 6).addBox(-1.0F, -9.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 16).addBox(1.0F, -9.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 4).addBox(1.0F, -9.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 14).addBox(-1.0F, -8.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 34).addBox(-1.0F, -8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 33).addBox(-1.0F, -8.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 30).addBox(-1.0F, -8.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 8).addBox(1.0F, -7.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 10).addBox(-1.0F, -7.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 28).addBox(-1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 20).addBox(-2.0F, -7.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 26).addBox(3.0F, -8.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 26).addBox(-3.0F, -8.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(26, 16).addBox(6.0F, -7.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 30).addBox(-6.0F, -7.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(32, 2).addBox(7.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 4).addBox(-7.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 6).addBox(4.0F, -8.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(8, 18).addBox(-4.0F, -8.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(5.0F, -8.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(20, 6).addBox(-5.0F, -8.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(PeridotSwordAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);

		this.animate(((PeridotSwordEntity) entity).idleAnimationState, PeridotSwordAnimationDefenitions.IDLE, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}
}