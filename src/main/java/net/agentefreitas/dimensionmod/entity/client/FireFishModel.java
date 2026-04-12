package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.DiscipleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.animations.FireFishAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireFishEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FireFishModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart fish;
    private final ModelPart body;
    private final ModelPart right_fin;
    private final ModelPart left_fin;
    private final ModelPart fin_top;
    private final ModelPart tail;
    private final ModelPart fin_left;
    private final ModelPart fin_right;

    public FireFishModel(ModelPart root) {
        this.fish = root.getChild("fish");
        this.body = this.fish.getChild("body");
        this.right_fin = this.body.getChild("right_fin");
        this.left_fin = this.body.getChild("left_fin");
        this.fin_top = this.fish.getChild("fin_top");
        this.tail = this.fish.getChild("tail");
        this.fin_left = this.fish.getChild("fin_left");
        this.fin_right = this.fish.getChild("fin_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fish = partdefinition.addOrReplaceChild("fish", CubeListBuilder.create(), PartPose.offset(0.0F, 22.5F, 0.0F));

        PartDefinition body = fish.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.5F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(16, 2).addBox(0.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition fin_top = fish.addOrReplaceChild("fin_top", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -3.0F));

        PartDefinition tail = fish.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 9).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition fin_left = fish.addOrReplaceChild("fin_left", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition fin_right = fish.addOrReplaceChild("fin_right", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(FireFishAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FireFishEntity) entity).idleAnimationState, FireFishAnimationDefenitions.IDLE, ageInTicks, 1f);
        this.animate(((FireFishEntity) entity).attackAnimationState, FireFishAnimationDefenitions.ATTACK, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.body.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.body.xRot = pHeadPitch * ((float)Math.PI / 180F);

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        fish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return fish;
    }
}
