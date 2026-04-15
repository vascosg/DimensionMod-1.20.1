package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.FireCowAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.FireFishAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.FireChickenEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireCowEntity;
import net.agentefreitas.dimensionmod.entity.custom.FireFishEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FireCowModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart cow;
    private final ModelPart leg4;
    private final ModelPart leg3;
    private final ModelPart leg2;
    private final ModelPart leg1;
    private final ModelPart body;
    private final ModelPart head;

    public FireCowModel(ModelPart root) {
        this.cow = root.getChild("cow");
        this.leg4 = this.cow.getChild("leg4");
        this.leg3 = this.cow.getChild("leg3");
        this.leg2 = this.cow.getChild("leg2");
        this.leg1 = this.cow.getChild("leg1");
        this.body = this.cow.getChild("body");
        this.head = this.cow.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cow = partdefinition.addOrReplaceChild("cow", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg4 = cow.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(16, 44).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -12.0F, -6.0F));

        PartDefinition leg3 = cow.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -12.0F, -6.0F));

        PartDefinition leg2 = cow.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 42).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -12.0F, 7.0F));

        PartDefinition leg1 = cow.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(28, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -12.0F, 7.0F));

        PartDefinition body = cow.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(44, 20).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head = cow.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(44, 27).addBox(4.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 31).addBox(-5.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 16).addBox(-3.0F, 1.0F, -7.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(FireCowAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FireCowEntity) entity).idleAnimationState, FireCowAnimationDefenitions.IDLE, ageInTicks, 1f);
        this.animate(((FireCowEntity) entity).attackAnimationState, FireCowAnimationDefenitions.ATTACK, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        cow.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return cow;
    }
}
