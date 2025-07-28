package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.AbstractArrow;


public class MoonBowItem extends BowItem {
    public MoonBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        boolean isDark = level.getMaxLocalRawBrightness(player.blockPosition()) < 10;

        if (isDark) {
            ItemStack arrow = new ItemStack(Items.ARROW, 1);
            if (!player.getInventory().contains(arrow)) {
                if (!player.getInventory().add(arrow)) {
                    player.drop(arrow, false);
                }
            }
        }

        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        boolean isInCreativeOrInfinity = player.getAbilities().instabuild ||
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;

        ItemStack ammo = player.getProjectile(stack);

        // Recarrega ammo depois de possivelmente ter dado uma
        if (!ammo.isEmpty() || isInCreativeOrInfinity) {
            float velocity = getPowerForTime(this.getUseDuration(stack) - timeLeft);
            if (velocity < 0.1F) return;

            if (!level.isClientSide) {
                ArrowItem arrowitem = ammo.getItem() instanceof ArrowItem ? (ArrowItem) ammo.getItem() : (ArrowItem) Items.ARROW;
                AbstractArrow arrow = arrowitem.createArrow(level, ammo, player);
                arrow.setCritArrow(velocity == 1.0F);
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity * 3.0F, 1.0F);

                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
                level.addFreshEntity(arrow);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                    1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

            if (!isInCreativeOrInfinity) {
                ammo.shrink(1);
                if (ammo.isEmpty()) {
                    player.getInventory().removeItem(ammo);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }



    public static float getPowerForTime(int charge) {
        float f = charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        return Math.min(f, 1.0F);
    }
}