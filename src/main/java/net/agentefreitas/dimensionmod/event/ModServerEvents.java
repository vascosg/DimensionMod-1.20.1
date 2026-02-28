package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.agentefreitas.dimensionmod.item.custom.RainbowNameItem;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModServerEvents {
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

    //ENCHANTS
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) { //LIFE STEAL
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