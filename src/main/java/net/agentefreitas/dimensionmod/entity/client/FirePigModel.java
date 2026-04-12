package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.FirePigAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.animations.LittlePupleAnimationDefinitions;
import net.agentefreitas.dimensionmod.entity.custom.FirePigEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class FirePigModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart pig_body;
    private final ModelPart pig;
    private final ModelPart head;
    private final ModelPart Nouse;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg1;
    private final ModelPart body;
    private final ModelPart leg4;

    public FirePigModel(ModelPart root) {
        this.pig_body = root.getChild("pig_body");
        this.pig = this.pig_body.getChild("pig");
        this.head = this.pig.getChild("head");
        this.Nouse = this.head.getChild("Nouse");
        this.leg2 = this.pig.getChild("leg2");
        this.leg3 = this.pig.getChild("leg3");
        this.leg1 = this.pig.getChild("leg1");
        this.body = this.pig.getChild("body");
        this.leg4 = this.pig.getChild("leg4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pig_body = partdefinition.addOrReplaceChild("pig_body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition pig = pig_body.addOrReplaceChild("pig", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = pig.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -6.0F));

        PartDefinition Nouse = head.addOrReplaceChild("Nouse", CubeListBuilder.create().texOffs(36, 20).addBox(-2.0F, -12.0F, -15.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 6.0F));

        PartDefinition leg2 = pig.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(36, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, 7.0F));

        PartDefinition leg3 = pig.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(36, 10).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, -5.0F));

        PartDefinition leg1 = pig.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(32, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, 7.0F));

        PartDefinition body = pig.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition leg4 = pig.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(32, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(FirePigAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((FirePigEntity) entity).idleAnimationState, FirePigAnimationDefenitions.IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        pig_body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return pig_body;
    }
}
