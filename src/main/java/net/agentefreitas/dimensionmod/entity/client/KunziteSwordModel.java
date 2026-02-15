package net.agentefreitas.dimensionmod.entity.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;


public class KunziteSwordModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart body;
    private final ModelPart kunzite_sword;
    private final ModelPart blade;
    private final ModelPart handle;

    public KunziteSwordModel(ModelPart root) {
        this.body = root.getChild("body");
        this.kunzite_sword = this.body.getChild("kunzite_sword");
        this.blade = this.kunzite_sword.getChild("blade");
        this.handle = this.kunzite_sword.getChild("handle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 17.0F, 7.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition kunzite_sword = body.addOrReplaceChild("kunzite_sword", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition blade = kunzite_sword.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -28.0F, 7.0F, 2.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(1.0F, -34.0F, 7.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 11).addBox(-1.0F, -15.0F, 7.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-1.0F, -24.0F, 7.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.0F, -8.0F));

        PartDefinition handle = kunzite_sword.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(10, 4).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(-2.0F, -6.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 4).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 6).addBox(-3.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 8).addBox(-2.0F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

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
