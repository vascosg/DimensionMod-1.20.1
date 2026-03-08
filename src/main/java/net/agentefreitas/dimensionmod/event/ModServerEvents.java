package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.custom.RainbowNameItem;
import net.agentefreitas.dimensionmod.packet.Messages;
import net.agentefreitas.dimensionmod.packet.SyncFocusPacket;
import net.agentefreitas.dimensionmod.particle.ModParticleTypes;
import net.agentefreitas.dimensionmod.worldgen.StructureSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModServerEvents {

    private static final Map<UUID, Integer> sprintingTicks = new HashMap<>(); //SPEED ENCH

    private static final Map<UUID, Integer> focusTime = new HashMap<>();     // EYE ENCH
    private static final Map<UUID, Integer> lookCooldowns = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        Player player = event.player;

        if (player.getMainHandItem().getItem() == ModItems.MOON_BOW.get()) {
            int lightLevel = player.level().getMaxLocalRawBrightness(player.blockPosition());

            if (lightLevel < 7) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false));
            }
        }

        performOneThousandStepArt(player);
        performEndlessStaringEyeArt(player);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MinecraftServer server = event.getServer();

        // Usa o Level.RESOURCE_KEY, não Registries.DIMENSION
        ResourceKey<Level> AQUA_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "aqua_dimension"));

        ServerLevel customLevel = server.getLevel(AQUA_DIMENSION);


        if (customLevel != null) {
            ResourceLocation bridgeStartId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_start");
            ResourceLocation bridgeMiddleId = new ResourceLocation("dimensionmod", "aqua_ruin/aqua_bridge_middle");
            ResourceLocation centerBaseId = new ResourceLocation( "dimensionmod", "aqua_ruin/aqua_center_base");
            ResourceLocation centerTopId = new ResourceLocation( "dimensionmod", "aqua_ruin/aqua_center_top");

            BlockPos pos = new BlockPos(0, 0, 0);
            StructureSpawner.generate(customLevel, pos,bridgeStartId);

            for(int i = 1; i < 20; i++) {
                pos = new BlockPos((48*i),-1,0);
                StructureSpawner.generate(customLevel, pos,bridgeMiddleId);
                if (i == 19 ) {
                    pos = new BlockPos((48*(i+1)),0,-10);
                    StructureSpawner.generate(customLevel, pos,centerBaseId);
                    pos = new BlockPos((48*(i+1)),48,-10);
                    StructureSpawner.generate(customLevel, pos,centerTopId);
                }
            }


        } else {
            System.out.println("❌ Dimensão não encontrada: " + AQUA_DIMENSION.location());
        }


    }


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        performLifeSteal(event);
        performeOneThousandStepArtReduceDmg(event);
        performBlurryFateArt(event);

    }

    private static void performLifeSteal(LivingHurtEvent event) {//LIFE STEAL
        // Verifica se quem atacou foi um Player
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();

            // Verifica o nível do encantamento na arma
            int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.LIFE_STEAL.get(), weapon);

            if (level > 0) {
                // Calcula quanto curar (ex: 10% do dano causado por nível)
                float damageDealt = event.getAmount();
                float healAmount = damageDealt * (level * 0.1f);

                // Cura o jogador
                player.heal(healAmount);

                // Opcional: Adiciona umas partículas de coração para dar feedback visual
                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.HEART,
                            player.getX(), player.getY() + 1.5, player.getZ(),
                            5, 0.2, 0.2, 0.2, 0.1);
                }
            }
        }
    }


    private static void performOneThousandStepArt(Player player) {
        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ONE_THOUSAND_STEP_ART.get(),
                player.getItemBySlot(EquipmentSlot.FEET));

        UUID playerUUID = player.getUUID();

        // 1. Verificar se está a correr e tem as botas
        if (level > 0 && player.isSprinting()) {
            int ticks = sprintingTicks.getOrDefault(playerUUID, 0);

            // Aumenta o contador
            if (ticks < 100) {
                ticks++;
                sprintingTicks.put(playerUUID, ticks);
            }

            // 2. Calcula o nível do efeito Speed (0 a 4, por exemplo) baseado nos ticks
            // A cada 20 ticks (1s), o bónus sobe.
            // Se level for 3, a velocidade máxima será Speed 3 ou 4.
            int speedAmplifier = (ticks / 25) * (level / 2 + 1);

            if (speedAmplifier >= 0) {
                // Aplicamos o efeito com duração curta (2 ticks) para ele se renovar constantemente
                // false, false esconde as partículas de poção para não atrapalhar a visão
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, speedAmplifier, false, false));
            }

            // Partículas customizadas do Zord quando está rápido
            if (ticks > 50 && player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 1, 0.1, 0, 0.1, 0.05);
            }

        } else {
            // 3. Reset total se parar de correr
            if (sprintingTicks.containsKey(playerUUID)) {
                sprintingTicks.remove(playerUUID);
                // Remove o efeito speed para parar na hora
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }
        }
    }

    private static void performeOneThousandStepArtReduceDmg(LivingHurtEvent event) {

        if (event.getSource().is(net.minecraft.world.damagesource.DamageTypes.FALL)) {
            int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ONE_THOUSAND_STEP_ART.get(),
                    event.getEntity().getItemBySlot(EquipmentSlot.FEET));
            if (level > 0) {
                // Reduz 25% do dano por nível (Nível 4 = Imune)
                float multiplier = Math.max(0, 1.0f - (level * 0.25f));
                event.setAmount(event.getAmount() * multiplier);
            }
        }
    }

    private static void performEndlessStaringEyeArt(Player player) {
        UUID pUUID = player.getUUID();

        // 1. Gerir Cooldown
        int cooldown = lookCooldowns.getOrDefault(pUUID, 0);
        if (cooldown > 0) {
            lookCooldowns.put(pUUID, cooldown - 1);
            return;
        }

        // 2. Verificar Encantamento no Capacete
        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ENDLESS_STARING_EYE_ART.get(),
                player.getItemBySlot(EquipmentSlot.HEAD));

        if (level > 0) {

            // --- RAYCASTING ---
            Vec3 eyePos = player.getEyePosition();
            Vec3 lookVec = player.getViewVector(1.0F);
            Vec3 reachVec = eyePos.add(lookVec.scale(15.0D));
            AABB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(15.0D)).inflate(1.0D);

            // Aqui usamos o entityHit que criaste
            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    player.level(),
                    player,
                    eyePos,
                    reachVec,
                    searchBox,
                    ModServerEvents::isValidLookTarget
            );

            // 3. Verificação de Alvo (Simplificada porque entityHit já é do tipo certo)
            if (entityHit != null && entityHit.getEntity() instanceof LivingEntity victim) {
                UUID vUUID = victim.getUUID();
                int currentFocus = focusTime.getOrDefault(vUUID, 0) + 1;
                focusTime.put(vUUID, currentFocus);

                System.out.println("Focando em: " + victim.getName().getString() + " | Progresso: " + currentFocus);

                // No final da parte onde o foco aumenta:
                Messages.sendToPlayer(new SyncFocusPacket(currentFocus), (ServerPlayer) player);

                int requiredTicks = 100;
                if (currentFocus >= 40 && currentFocus < 100) {
                    int interval = 30; //(currentFocus > 75) ? 10 : 20;


                    if (currentFocus % interval == 0) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                net.minecraft.sounds.SoundEvents.WARDEN_HEARTBEAT,
                                net.minecraft.sounds.SoundSource.PLAYERS,
                                1.0F,
                                0.8F + (currentFocus / 100.0F));
                    }
                }

                // Feedback visual de carregamento
                if (currentFocus % 5 == 0 && player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, victim.getX(), victim.getEyeY(), victim.getZ(), 3, 0.1, 0.1, 0.1, 0.02);
                }

                if (currentFocus >= requiredTicks) {
                    // Dano Mágico
                    victim.hurt(player.damageSources().magic(), 6.0F * level);

                    if (level >= 2) {
                        // Slowness I (amplificador 0) se for lvl 2, Slowness II (amplificador 1) se for lvl 3
                        int slownessLevel = (level >= 3) ? 1 : 0;
                        victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, slownessLevel));

                        if (level >= 3) {
                            // Aplica Weakness (Fraqueza) apenas no nível 3
                            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                        }
                    }

                    if (player.level() instanceof ServerLevel serverLevel) {
                        // 1. O "Flash" central para dar impacto
                        serverLevel.sendParticles(ParticleTypes.FLASH, victim.getX(), victim.getEyeY(), victim.getZ(), 1, 0, 0, 0, 0);

                        serverLevel.sendParticles(ModParticleTypes.EYE_STARING_PARTICLE.get(),
                                victim.getX(), victim.getEyeY() + 0.5, victim.getZ(),
                                1, 0, 0, 0, 0); // O '1' é a quantidade de olhos

                        // 2. Partículas Vermelhas (Explosão)
                        // Parâmetros: Particle, x, y, z, quantidade, dx, dy, dz, velocidade
                        // Para ENTITY_EFFECT, os campos dx, dy, dz funcionam como cores R, G, B (0.0 a 1.0)
                        for (int i = 0; i < 20; i++) {
                            serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT,
                                    victim.getX(), victim.getEyeY(), victim.getZ(), 0,
                                    1.0, 0.0, 0.0, 1.0); // Vermelho Puro (R=1.0, G=0.0, B=0.0)
                        }

                        // 3. Partículas Pretas (Fumo denso)
                        for (int i = 0; i < 15; i++) {
                            serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT,
                                    victim.getX(), victim.getEyeY(), victim.getZ(), 0,
                                    0.0, 0.0, 0.0, 1.0); // Preto (R=0, G=0, B=0)
                        }

                        // 4. Som de impacto mais pesado (opcional)
                        serverLevel.playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                                net.minecraft.sounds.SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.5F, 0.5F);
                    }

                    lookCooldowns.put(pUUID, 100);
                    Messages.sendToPlayer(new SyncFocusPacket(0), (ServerPlayer) player);
                    focusTime.remove(vUUID);
                }
            } else {
                if (!focusTime.isEmpty() ) {
                    System.out.println("Olhar desviado - Foco resetado.");
                    Messages.sendToPlayer(new SyncFocusPacket(0), (ServerPlayer) player);
                    focusTime.clear();
                }
                // Se o olhar não encontrar nada, resetamos o foco deste jogador
                focusTime.remove(pUUID);
            }
        }
    }

    private static boolean isValidLookTarget(net.minecraft.world.entity.Entity entity) {
        // Evita que o jogador foque em si mesmo ou em espetadores
        return !entity.isSpectator() && entity instanceof net.minecraft.world.entity.LivingEntity && entity.isPickable();
    }

    private static void performBlurryFateArt(LivingHurtEvent event){

        if (event.getEntity() instanceof Player player) {

            // 2. Verificamos se o Player tem o encantamento nas botas ou armadura
            // Podes escolher onde o encantamento deve estar (ex: PÉS ou PEITORAL)
            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.BLURRY_FATE_ART_ENCHANTMENT.get(), player);

            if (level > 0) {
                // 3. Cálculo da Chance (ex: 10% por nível)
                float chance = level * 0.2F;

                if (player.getRandom().nextFloat() < chance) {
                    // 4. CANCELA O DANO
                    event.setCanceled(true);

                    // 5. Feedback Visual (Opcional mas recomendado)
                    // Som de "esquiva" ou a tua partícula de Olho/Flash
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 2.0F);

                    if (player.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT,
                                player.getX(), player.getEyeY(), player.getZ(), 10, 0.2, 0.2, 0.2, 0.1);
                    }

                    if (level >= 3) {
                        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 0));
                    }
                }
            }

        }
    }

    //ANVILS RECIPES
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack leftItem = event.getLeft();   // Item no 1º slot
        ItemStack rightItem = event.getRight(); // Item no 2º slot

        // 1. Verificar se o item da esquerda é um livro normal
        // 2. Verificar se o item da direita é o material necessário (ex: Cursed Bone)
        if (leftItem.getItem() == Items.BOOK && rightItem.getItem() == ModItems.CURSED_BONE.get()) {//LIFE STEAL BOOK

            // Criar o Livro Encantado de saída
            ItemStack outputStack = new ItemStack(Items.ENCHANTED_BOOK);

            // Adicionar o encantamento Life Steal Nível 1 ao livro
            EnchantedBookItem.addEnchantment(outputStack, new EnchantmentInstance(ModEnchantments.LIFE_STEAL.get(), 1));

            // Definir o resultado e o custo em XP
            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_HEAD.get()) {//ZORD HEAD

            ItemStack outputStack = new ItemStack(ModItems.ZORD_HEAD.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_CHEST.get()) {//ZORD CHEST

            ItemStack outputStack = new ItemStack(ModItems.ZORD_CHEST.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_R_ARM.get()) {//ZORD RIGHT ARM

            ItemStack outputStack = new ItemStack(ModItems.ZORD_RIGHT_ARM.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_L_ARM.get()) {//ZORD LEFT ARM

            ItemStack outputStack = new ItemStack(ModItems.ZORD_LEFT_ARM.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_R_LEG.get()) {//ZORD RIGHT LEG

            ItemStack outputStack = new ItemStack(ModItems.ZORD_RIGHT_LEG.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }

        if (leftItem.getItem() == ModItems.MALLEABLE_MINERAL_CONDENSATE.get() && rightItem.getItem() == ModItems.ZORD_BP_L_LEG.get()) {//ZORD LEFT LEG

            ItemStack outputStack = new ItemStack(ModItems.ZORD_LEFT_LEG.get());

            event.setOutput(outputStack);
            event.setCost(5); // Custo de 5 níveis de XP
            event.setMaterialCost(1); // Consome apenas 1 osso do stack
        }
    }

}