package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.enchantments.ModEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID)
public class EvolutionEventHandler {

    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (hasEvolutionEnchant(stack) && (stack.getItem() instanceof SwordItem || stack.getItem() instanceof BowItem)) {
                updateEvolution(stack, player, "kills");
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getLevel().isClientSide()) {
            Player player = event.getPlayer();
            ItemStack stack = player.getMainHandItem();
            if (hasEvolutionEnchant(stack) && (stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof AxeItem)) {
                updateEvolution(stack, player, "blocks");
            }
        }
    }

    private static boolean hasEvolutionEnchant(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.EVER_EVOLVING_SPIRIT_ART_ENCHANTMENT.get(), stack) > 0;
    }

    private static void updateEvolution(ItemStack stack, Player player, String type) {
        CompoundTag nbt = stack.getOrCreateTag();
        int x = nbt.getInt("EvolutionLevel"); // Este é o teu 'x'
        int progress = nbt.getInt("EvolutionProgress"); // Contador atual

        int target = 100 + (100 * x); // Tua fórmula: 100, 200, 300...

        progress++;
        if (progress >= target) {
            x++;
            progress = 0;
            player.sendSystemMessage(Component.literal("§6Tua arma evoluiu para o nível " + x + "!"));
            player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0f, 1.0f);
        }

        nbt.putInt("EvolutionLevel", x);
        nbt.putInt("EvolutionProgress", progress);
    }

    @SubscribeEvent
    public static void onAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int x = stack.getOrCreateTag().getInt("EvolutionLevel");

        if (x > 0 && hasEvolutionEnchant(stack)) {
            if (event.getSlotType() == EquipmentSlot.MAINHAND) {
                Item item = stack.getItem();

                // Apenas Espadas (e Machados, se quiseres) ganham Dano
                // Se quiseres que o Machado NÃO ganhe dano, remove o '|| item instanceof AxeItem'
                if (item instanceof SwordItem) {
                    AttributeModifier damageModifier = new AttributeModifier(
                            UUID.fromString("48f98013-9031-4191-9e2e-2e4a8b7c3d4e"),
                            "Evolution Damage", (double) x, AttributeModifier.Operation.ADDITION);

                    event.addModifier(Attributes.ATTACK_DAMAGE, damageModifier);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();
        int x = stack.getOrCreateTag().getInt("EvolutionLevel");

        if (x > 0 && hasEvolutionEnchant(stack)) {
            // Aumenta a velocidade (ex: x * 0.5f para não ficar quebrado demais)
            event.setNewSpeed(event.getNewSpeed() + (x * 1.5f));
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (hasEvolutionEnchant(stack)) {
            CompoundTag nbt = stack.getTag();
            int x = nbt != null ? nbt.getInt("EvolutionLevel") : 0;
            int progress = nbt != null ? nbt.getInt("EvolutionProgress") : 0;
            int target = 100 + (100 * x);

            event.getToolTip().add(Component.literal("§d§lEspírito Evolutivo: §fNível " + x));
            event.getToolTip().add(Component.literal("§7Progresso: §e" + progress + "§7 / §e" + target));

            // Linha de Bónus Específico
            if (x > 0) {
                Item item = stack.getItem();
                if (item instanceof net.minecraft.world.item.PickaxeItem || item instanceof net.minecraft.world.item.AxeItem) {
                    // Mostra a velocidade extra (usando o multiplicador 1.5f que sugerimos antes)
                    float speedBonus = x * 1.5f;
                    event.getToolTip().add(Component.literal("§b+ " + speedBonus + " Velocidade de Mineração"));
                } else if (item instanceof net.minecraft.world.item.SwordItem) {
                    // Mostra o dano extra
                    event.getToolTip().add(Component.literal("§c+ " + x + " Dano de Ataque"));
                } else if (item instanceof net.minecraft.world.item.BowItem) {
                    // Mostra o dano extra para arcos
                    event.getToolTip().add(Component.literal("§a+ " + x + " Força de Disparo"));
                }
            }
        }
    }

    // Melhor ainda: usar o EntityJoinLevelEvent para modificar a flecha quando ela nasce
    @SubscribeEvent
    public static void onArrowSpawn(EntityJoinLevelEvent event) {
        // 1. Verifica se o que está a entrar no mundo é uma flecha
        if (event.getEntity() instanceof AbstractArrow arrow) {
            // 2. Verifica se foi um player que a disparou
            if (arrow.getOwner() instanceof Player player) {
                ItemStack bow = player.getMainHandItem();

                // 3. Verifica se o arco tem o encantamento
                if (hasEvolutionEnchant(bow)) {
                    int x = bow.getOrCreateTag().getInt("EvolutionLevel");
                    if (x > 0) {
                        // 4. Aplica o bónus de dano (x) ao dano base da flecha
                        arrow.setBaseDamage(arrow.getBaseDamage() + x * 3);

                    }
                }
            }
        }
    }
}