package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class SunSwordItem extends SwordItem {

    public SunSwordItem(Tier tier, int attackDamageModifier, float attackSpeed, Properties properties) {
        super(tier, attackDamageModifier, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        // Checa se está de dia e a luz local é alta (pode ajustar o valor)
        if (!level.isClientSide && level.getMaxLocalRawBrightness(attacker.blockPosition()) >= 12) {
            target.setSecondsOnFire(4);

            // Aplica dano extra
            target.hurt(attacker.level().damageSources().mobAttack(attacker), 7.0F); // dano extra (ajuste se quiser)
        }

        BlockPos pos = attacker.blockPosition();
        int light = level.getMaxLocalRawBrightness(pos);

        if (light >= 12) {
            for (int i = 0; i < 5; i++) {
                level.addParticle(
                        new DustParticleOptions(new Vector3f(1.0F, 1.0F, 0.0F), 1.0F), // RGB amarelo
                        target.getX() + (level.random.nextDouble() - 0.5),
                        target.getY() + 1.0,
                        target.getZ() + (level.random.nextDouble() - 0.5),
                        0, 0.05, 0
                );
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}