package net.agentefreitas.dimensionmod.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.CitrineSwordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.GarnetSwordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.CitrineSwordEntity;
import net.agentefreitas.dimensionmod.entity.custom.GarnetSwordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;


public class CitrineSwordModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart body;
	private final ModelPart citrine_sword;
	private final ModelPart handle;
	private final ModelPart blade;

	public CitrineSwordModel(ModelPart root) {
		this.body = root.getChild("body");
		this.citrine_sword = this.body.getChild("citrine_sword");
		this.handle = this.citrine_sword.getChild("handle");
		this.blade = this.citrine_sword.getChild("blade");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition citrine_sword = body.addOrReplaceChild("citrine_sword", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition handle = citrine_sword.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(16, 36).addBox(-2.0F, -49.0F, -5.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(44, 40).addBox(-1.0F, -48.0F, -7.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 45).addBox(-1.0F, -48.0F, 5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 13).addBox(-3.0F, -47.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(40, 19).addBox(1.0F, -47.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(16, 49).addBox(-3.0F, -46.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(32, 49).addBox(1.0F, -46.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 49).addBox(-3.0F, -48.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 49).addBox(1.0F, -48.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(52, 9).addBox(-3.0F, -45.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 13).addBox(1.0F, -45.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 11).addBox(-3.0F, -49.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 15).addBox(1.0F, -49.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(-2.0F, -50.0F, -2.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(48, 49).addBox(-1.0F, -56.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 17).addBox(-1.0F, -51.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 19).addBox(-1.0F, -51.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 21).addBox(-1.0F, -50.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 23).addBox(-1.0F, -50.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -1.0F));

		PartDefinition blade = citrine_sword.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(40, 7).addBox(-1.0F, -8.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(50, 31).addBox(-1.0F, -4.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 50).addBox(-1.0F, -5.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 45).addBox(-1.0F, -6.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 25).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 0).addBox(-1.0F, -9.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(40, 30).addBox(-2.0F, -10.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 45).addBox(-2.0F, -9.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 25).addBox(-2.0F, -8.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(50, 33).addBox(-2.0F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -41.0F, -4.0F, 1.0F, 32.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(0.0F, -41.0F, -3.0F, 1.0F, 31.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-2.0F, -41.0F, -3.0F, 1.0F, 31.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(44, 35).addBox(0.0F, -10.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 49).addBox(0.0F, -9.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 28).addBox(0.0F, -8.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 7).addBox(0.0F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(CitrineSwordAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);

		this.animate(((CitrineSwordEntity) entity).idleAnimationState, CitrineSwordAnimationDefenitions.IDLE, ageInTicks, 1f);
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