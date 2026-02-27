package net.agentefreitas.dimensionmod.item.custom;

import net.agentefreitas.dimensionmod.entity.custom.AmberProjectileEntity;
import net.agentefreitas.dimensionmod.entity.custom.KunziteProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DualitySwordItem extends SwordItem {
    public DualitySwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Verifica se o jogador está agachado (Shift)
        if (pPlayer.isCrouching() && !pLevel.isClientSide()) {

            // Lógica de Revezamento usando NBT
            CompoundTag nbt = itemstack.getOrCreateTag();
            boolean shootKunzite = nbt.getBoolean("toggle_projectile");

            if (shootKunzite) {
                // Dispara 3 Kunzite (Lentos e Espalhados)
                shootKunziteProjectiles(pLevel, pPlayer);
            } else {
                // Dispara 1 Amber (Rápido e Preciso)
                shootAmberProjectile(pLevel, pPlayer);
            }

            // Inverte o valor para a próxima vez
            nbt.putBoolean("toggle_projectile", !shootKunzite);

            // Adiciona um cooldown para não disparar 20 vezes por segundo
            pPlayer.getCooldowns().addCooldown(this, 10);

            return InteractionResultHolder.success(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    private void shootAmberProjectile(Level world, Player player) {
        AmberProjectileEntity projectile = new AmberProjectileEntity(world, player);
        Vec3 look = player.getLookAngle();

        projectile.setPos(player.getX() + look.x, player.getEyeY() - 0.1, player.getZ() + look.z);
        // 1.6F é a velocidade rápida
        projectile.shoot(look.x, look.y, look.z, 1.6F, 1.0F);

        world.addFreshEntity(projectile);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.2F);
    }

    private void shootKunziteProjectiles(Level world, Player player) {
        Vec3 look = player.getLookAngle();

        for (int i = 0; i < 3; i++) {
            KunziteProjectileEntity projectile = new KunziteProjectileEntity(world, player);

            // Posição com offset aleatório
            double offsetX = (player.getRandom().nextDouble() - 0.5D) * 1.5D;
            double offsetY = (player.getRandom().nextDouble() - 0.5D) * 1.0D;
            double offsetZ = (player.getRandom().nextDouble() - 0.5D) * 1.5D;

            projectile.setPos(player.getX() + offsetX, player.getEyeY() + offsetY, player.getZ() + offsetZ);

            // 0.4F é a velocidade lenta, 10.0F é a imprecisão (espalhamento)
            projectile.shoot(look.x, look.y, look.z, 0.4F, 10.0F);

            world.addFreshEntity(projectile);
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 0.5F);
    }
}
