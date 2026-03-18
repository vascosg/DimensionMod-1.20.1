package net.agentefreitas.dimensionmod.block.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class MobFigureBlockEntityRenderer implements BlockEntityRenderer<MobFigureBlockEntity> {

    private static final Map<String, Entity> ENTITY_CACHE = new HashMap<>();

    public MobFigureBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        // O contexto pode ser usado para buscar outros renderers se necessário
    }

    @Override
    public void render(MobFigureBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        String mobId = pBlockEntity.getMobId();
        Level level = pBlockEntity.getLevel();



        if (level == null || mobId == null || mobId.isEmpty()) return;

        if (mobId.isEmpty()) {
            // Se imprimir isto, o problema é a sincronização Servidor -> Cliente
            // System.out.println("[DEBUG-RENDER] MobId está VAZIO no renderer!");
        }

        // 1. Obter a entidade do Cache (evita criar 60 entidades por segundo)
        Entity entity = ENTITY_CACHE.computeIfAbsent(mobId, id -> {
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(id));
            if (type != null) {
                Entity e = type.create(level);
                if (e != null) {
                    e.setInvulnerable(true); // Para de sofrer dano (ficar vermelho)
                    e.setSilent(true);       // Para de fazer barulho
                    return e;
                }
            }
            return null;
        });

        if (entity == null) return;

        // 2. Aplicar a variante específica deste bloco (Cor, Padrão, etc.)
        CompoundTag data = pBlockEntity.getMobData();
        if (data != null) {
            entity.load(data);

            // Verificamos se é uma LivingEntity para acessar vida e animação de morte
            if (entity instanceof LivingEntity living && living.getHealth() < living.getMaxHealth()) {

                living.setHealth(living.getMaxHealth()); // Cura o mob
                living.deathTime = 0;                    // Reseta a animação de tombar
                living.hurtTime = 0;                     // Para de piscar vermelho

                living.yBodyRot = 0; // Reseta a rotação do corpo
                living.yBodyRotO = 0;
                living.yHeadRot = 0;  // Reseta a rotação da cabeça
                living.yHeadRotO = 0;
                living.tickCount = 0; // Impede que a entidade "envelheça" e processe IA

                // Impede que o mob "envelheça" (para a IA)
                living.tickCount = 0;

                // ESTA É A PARTE QUE SUBSTITUI O animationSpeed:
                // Congela a animação de caminhada/pernas
                living.walkAnimation.setSpeed(0f);
                living.walkAnimation.position(0f);
            }
        }

        pPoseStack.pushPose();

        // 3. Posicionamento e Escala
        pPoseStack.translate(0.5D, 0.1D, 0.5D);

        float scale = 0.2f;
        if (mobId.equals("minecraft:ender_dragon")) {
            scale = 0.05f;
        } else if (mobId.equals("minecraft:warden") || mobId.equals("minecraft:ghast")) {
            scale = 0.1f;
        }
        pPoseStack.scale(scale, scale, scale);

        // 4. Rotação
        long time = level.getGameTime();
        pPoseStack.mulPose(Axis.YP.rotationDegrees((time + pPartialTick) * 2.0F));

        // 5. Renderização
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.setRenderShadow(false);

        dispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, pPartialTick,
                pPoseStack, pBufferSource, pPackedLight);

        dispatcher.setRenderShadow(true);
        pPoseStack.popPose();
    }
}