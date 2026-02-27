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

    public DuplicatorFlowerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level();

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // 1. Impedir que duplique o próprio Player ou o Wither/Ender Dragon (opcional)
            if (target instanceof Player || !target.canChangeDimensions()) {
                return InteractionResult.PASS;
            }

            // 2. Criar a nova entidade
            EntityType<? extends Entity> type = target.getType();
            Entity duplicate = type.create(serverLevel);

            if (duplicate != null) {
                // 3. Copiar os dados (NBT) da entidade original para a nova
                // Isso garante que se for uma ovelha azul, a cópia também seja azul
                CompoundTag nbt = target.saveWithoutId(new CompoundTag());
                duplicate.load(nbt);

                // 4. Mover a cópia ligeiramente para o lado para não ficarem presas uma na outra
                duplicate.setPos(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                duplicate.setUUID(java.util.UUID.randomUUID()); // Gerar novo UUID para não haver conflitos

                // 5. Adicionar a entidade ao mundo
                serverLevel.addFreshEntity(duplicate);

                // 6. Aplicar o Cooldown ao item para o jogador
                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}