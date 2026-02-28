package net.agentefreitas.dimensionmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.agentefreitas.dimensionmod.entity.animations.OrangeZordAnimationDefenitions;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class OrangeZordModel <T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bone;
    private final ModelPart CABECA;
    private final ModelPart PESCOCO;
    private final ModelPart tronco;
    private final ModelPart OMBROS;
    private final ModelPart OMBROdireito;
    private final ModelPart OMBROesquerdo;
    private final ModelPart PEITO;
    private final ModelPart detalhes2;
    private final ModelPart CINTURA;
    private final ModelPart BRACOS;
    private final ModelPart BracoEsquerdo;
    private final ModelPart BracoDireito;
    private final ModelPart CUECA;
    private final ModelPart detalhes;
    private final ModelPart PERNAS;
    private final ModelPart PernaEsquerda;
    private final ModelPart PDESQUERDO;
    private final ModelPart JOALHO2;
    private final ModelPart PernaDireita;
    private final ModelPart PDIREITO;
    private final ModelPart JOALHO;

    public OrangeZordModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.CABECA = this.bone.getChild("CABECA");
        this.PESCOCO = this.CABECA.getChild("PESCOCO");
        this.tronco = this.bone.getChild("tronco");
        this.OMBROS = this.tronco.getChild("OMBROS");
        this.OMBROdireito = this.OMBROS.getChild("OMBROdireito");
        this.OMBROesquerdo = this.OMBROS.getChild("OMBROesquerdo");
        this.PEITO = this.tronco.getChild("PEITO");
        this.detalhes2 = this.PEITO.getChild("detalhes2");
        this.CINTURA = this.tronco.getChild("CINTURA");
        this.BRACOS = this.tronco.getChild("BRACOS");
        this.BracoEsquerdo = this.BRACOS.getChild("BracoEsquerdo");
        this.BracoDireito = this.BRACOS.getChild("BracoDireito");
        this.CUECA = this.bone.getChild("CUECA");
        this.detalhes = this.CUECA.getChild("detalhes");
        this.PERNAS = this.bone.getChild("PERNAS");
        this.PernaEsquerda = this.PERNAS.getChild("PernaEsquerda");
        this.PDESQUERDO = this.PernaEsquerda.getChild("PDESQUERDO");
        this.JOALHO2 = this.PernaEsquerda.getChild("JOALHO2");
        this.PernaDireita = this.PERNAS.getChild("PernaDireita");
        this.PDIREITO = this.PernaDireita.getChild("PDIREITO");
        this.JOALHO = this.PernaDireita.getChild("JOALHO");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition CABECA = bone.addOrReplaceChild("CABECA", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -5.0F, -4.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 38).addBox(-4.0F, -4.0F, -4.0F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 38).addBox(3.0F, -4.0F, -4.0F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 48).addBox(-3.0F, -4.0F, 3.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 55).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 83).addBox(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 83).addBox(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 21).addBox(-3.0F, 2.0F, -4.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 1.0F));

        PartDefinition PESCOCO = CABECA.addOrReplaceChild("PESCOCO", CubeListBuilder.create().texOffs(24, 51).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(62, 10).addBox(-2.0F, 2.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 46).addBox(-2.0F, 2.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -1.0F));

        PartDefinition cube_r1 = PESCOCO.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(76, 7).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = PESCOCO.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(76, 0).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tronco = bone.addOrReplaceChild("tronco", CubeListBuilder.create(), PartPose.offset(0.0F, -26.0F, 0.0F));

        PartDefinition OMBROS = tronco.addOrReplaceChild("OMBROS", CubeListBuilder.create(), PartPose.offset(-8.0F, -6.0F, 0.0F));

        PartDefinition OMBROdireito = OMBROS.addOrReplaceChild("OMBROdireito", CubeListBuilder.create().texOffs(52, 17).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-3.0F, -2.0F, -4.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition OMBROesquerdo = OMBROS.addOrReplaceChild("OMBROesquerdo", CubeListBuilder.create().texOffs(40, 51).addBox(7.0F, -32.0F, -3.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 29).addBox(6.0F, -34.0F, -4.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 32.0F, 0.0F));

        PartDefinition PEITO = tronco.addOrReplaceChild("PEITO", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, -3.0F, 12.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition detalhes2 = PEITO.addOrReplaceChild("detalhes2", CubeListBuilder.create().texOffs(28, 13).addBox(-1.0F, -2.0F, -4.0F, 14.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 7).addBox(0.0F, -5.0F, 3.0F, 12.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 34).addBox(1.0F, -1.0F, 3.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 25).addBox(4.0F, -2.0F, -5.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 0).addBox(3.0F, -3.0F, -5.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(78, 31).addBox(0.0F, -5.0F, -4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 35).addBox(10.0F, -5.0F, -4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 17).addBox(1.0F, 1.0F, -4.0F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 8).addBox(3.0F, 3.0F, -4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 1.0F, 0.0F));

        PartDefinition cube_r3 = detalhes2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(34, 75).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = detalhes2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 75).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r5 = detalhes2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(72, 39).addBox(-2.0F, -5.0F, -1.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 10).addBox(8.0F, -5.0F, -1.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = detalhes2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(72, 31).addBox(-2.0F, -5.0F, -1.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, -2.0F, -1.5708F, 0.0F, 1.5708F));

        PartDefinition cube_r7 = detalhes2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(70, 48).addBox(-2.0F, -5.0F, -1.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -3.0F, -2.0F, -1.5708F, 0.0F, 1.5708F));

        PartDefinition CINTURA = tronco.addOrReplaceChild("CINTURA", CubeListBuilder.create().texOffs(48, 29).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition BRACOS = tronco.addOrReplaceChild("BRACOS", CubeListBuilder.create(), PartPose.offset(12.0F, 7.0F, 1.0F));

        PartDefinition BracoEsquerdo = BRACOS.addOrReplaceChild("BracoEsquerdo", CubeListBuilder.create().texOffs(24, 58).addBox(-1.0F, -8.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 63).addBox(0.0F, -7.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 2.0F, -1.0F));

        PartDefinition cube_r8 = BracoEsquerdo.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(44, 58).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r9 = BracoEsquerdo.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(28, 67).addBox(0.0F, -3.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r10 = BracoEsquerdo.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(20, 67).addBox(0.0F, -3.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.0F, -2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition BracoDireito = BRACOS.addOrReplaceChild("BracoDireito", CubeListBuilder.create().texOffs(34, 58).addBox(0.0F, -6.0F, -3.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(10, 63).addBox(-3.0F, -5.0F, -2.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r11 = BracoDireito.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(68, 17).addBox(0.0F, -3.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r12 = BracoDireito.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(52, 62).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.0F, -1.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r13 = BracoDireito.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(60, 67).addBox(0.0F, -3.0F, -2.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, -3.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition CUECA = bone.addOrReplaceChild("CUECA", CubeListBuilder.create().texOffs(36, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition detalhes = CUECA.addOrReplaceChild("detalhes", CubeListBuilder.create().texOffs(68, 31).addBox(4.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(80, 75).addBox(7.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 75).addBox(5.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -5.0F, -4.0F));

        PartDefinition PERNAS = bone.addOrReplaceChild("PERNAS", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition PernaEsquerda = PERNAS.addOrReplaceChild("PernaEsquerda", CubeListBuilder.create().texOffs(0, 50).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 68).addBox(-1.0F, 2.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 20).addBox(-1.0F, 2.0F, 3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(74, 78).addBox(0.0F, 4.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -19.0F, -1.0F));

        PartDefinition cube_r14 = PernaEsquerda.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(76, 17).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 4.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r15 = PernaEsquerda.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(8, 76).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 4.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r16 = PernaEsquerda.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(82, 2).addBox(-1.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r17 = PernaEsquerda.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 62).addBox(-3.0F, -9.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r18 = PernaEsquerda.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(78, 66).addBox(-1.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r19 = PernaEsquerda.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(78, 39).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 5.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r20 = PernaEsquerda.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(52, 72).addBox(0.0F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 5.0F, 4.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition PDESQUERDO = PernaEsquerda.addOrReplaceChild("PDESQUERDO", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(62, 3).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(48, 36).addBox(-1.0F, -1.0F, 1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(80, 71).addBox(0.0F, -4.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 83).addBox(0.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 83).addBox(0.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 2.0F));

        PartDefinition cube_r21 = PDESQUERDO.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(52, 24).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r22 = PDESQUERDO.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(76, 54).addBox(-1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r23 = PDESQUERDO.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(76, 23).addBox(-1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition JOALHO2 = PernaEsquerda.addOrReplaceChild("JOALHO2", CubeListBuilder.create().texOffs(44, 74).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 58).addBox(-2.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 63).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 43).addBox(-1.0F, -2.0F, 1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 76).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 79).addBox(-2.0F, 2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 79).addBox(0.0F, 2.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 75).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(82, 45).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 11.0F, 1.0F));

        PartDefinition cube_r24 = JOALHO2.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(78, 29).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r25 = JOALHO2.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(78, 27).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition PernaDireita = PERNAS.addOrReplaceChild("PernaDireita", CubeListBuilder.create().texOffs(12, 50).addBox(-2.0F, -1.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 70).addBox(-2.0F, 2.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(-1.0F, 4.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 48).addBox(-2.0F, 2.0F, 3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -19.0F, -1.0F));

        PartDefinition cube_r26 = PernaDireita.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(72, 62).addBox(-3.0F, -9.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 17).addBox(0.0F, -9.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.0F, 1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r27 = PernaDireita.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(52, 76).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r28 = PernaDireita.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(76, 51).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 4.0F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r29 = PernaDireita.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(74, 3).addBox(0.0F, -6.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.0F, 4.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r30 = PernaDireita.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(82, 42).addBox(-1.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r31 = PernaDireita.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(78, 78).addBox(-1.0F, -10.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(69, 73).addBox(-1.0F, -9.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 8.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition PDIREITO = PernaDireita.addOrReplaceChild("PDIREITO", CubeListBuilder.create().texOffs(78, 13).addBox(-1.0F, -1.0F, 4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 62).addBox(-1.0F, -3.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 42).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(80, 60).addBox(0.0F, -4.0F, 4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 83).addBox(0.0F, -4.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(82, 5).addBox(0.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 19.0F, -1.0F));

        PartDefinition cube_r32 = PDIREITO.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(78, 11).addBox(-1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r33 = PDIREITO.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(78, 9).addBox(-1.0F, -1.0F, -4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r34 = PDIREITO.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(58, 12).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition JOALHO = PernaDireita.addOrReplaceChild("JOALHO", CubeListBuilder.create().texOffs(70, 76).addBox(-1.0F, 0.0F, 1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 72).addBox(-1.0F, 0.0F, 3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 42).addBox(0.0F, 0.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 79).addBox(-2.0F, 4.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 74).addBox(-1.0F, 0.0F, 2.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 79).addBox(-2.0F, 0.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 79).addBox(0.0F, 4.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 75).addBox(-2.0F, 1.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 82).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -1.0F));

        PartDefinition cube_r35 = JOALHO.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(78, 25).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r36 = JOALHO.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(78, 15).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animateWalk(OrangeZordAnimationDefenitions.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((OrangeZordEntity) entity).idleAnimationState, OrangeZordAnimationDefenitions.IDLE, ageInTicks, 1f);
        this.animate(((OrangeZordEntity) entity).attackAnimationState, OrangeZordAnimationDefenitions.ATTACK, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 30.0F);

        this.CABECA.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.CABECA.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.bone;
    }
}
