package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DuplicatorFlowerItem extends RainbowNameItem {

    // Cooldown em ticks (20 ticks = 1 segundo). 1200 ticks = 1 minuto.
    // Podes aumentar para 6000 (5 minutos) ou o que desejares.
    private static final int COOLDOWN_TICKS = 12000;
    private static final String COOLDOWN_TAG = "NextUseTime";

    public DuplicatorFlowerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        Level level = player.level();
        long currentTime = level.getGameTime();

        // Identificador único da instância do objeto na memória (para ver se o stack muda)
        int stackId = System.identityHashCode(stack);

        // 1. Verificação Profunda do NBT
        CompoundTag tag = stack.getTag(); // Pegamos a tag real sem criar uma nova para o teste
        long nextUseTime = (tag != null && tag.contains(COOLDOWN_TAG)) ? tag.getLong(COOLDOWN_TAG) : 0;


        if (nextUseTime > currentTime) {
            return InteractionResult.FAIL;
        }

        // 2. Lógica do Servidor
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            if (target instanceof Player || !target.canChangeDimensions()) return InteractionResult.PASS;

            EntityType<? extends Entity> type = target.getType();
            Entity duplicate = type.create(serverLevel);

            if (duplicate != null) {
                // Gravar NBT usando getOrCreateTag para garantir a escrita
                CompoundTag serverTag = stack.getOrCreateTag();
                long newTargetTime = currentTime + COOLDOWN_TICKS;
                serverTag.putLong(COOLDOWN_TAG, newTargetTime);

                // Cooldown visual
                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

                // Sincronização forçada
                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                    serverPlayer.containerMenu.broadcastChanges();
                    // Forçar sincronização do slot específico
                    serverPlayer.inventoryMenu.slotsChanged(serverPlayer.getInventory());
                }

                // Duplicação
                CompoundTag entityNbt = target.saveWithoutId(new CompoundTag());
                duplicate.load(entityNbt);
                duplicate.setPos(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                duplicate.setUUID(java.util.UUID.randomUUID());

                if (serverLevel.addFreshEntity(duplicate)) {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (stack.getOrCreateTag().contains(COOLDOWN_TAG)) {
                long nextUse = stack.getOrCreateTag().getLong(COOLDOWN_TAG);
                long current = level.getGameTime();

                // Se o NBT diz que ainda há cooldown, mas a barra visual sumiu
                if (nextUse > current && !player.getCooldowns().isOnCooldown(this)) {
                    int remaining = (int) (nextUse - current);
                    player.getCooldowns().addCooldown(this, remaining);
                }
            }
        }
    }
}