package net.agentefreitas.dimensionmod.effect.custom;

import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PigCurseEffect extends MobEffect {

    public PigCurseEffect() {
        super(MobEffectCategory.HARMFUL, 0x240408);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 2. Lógica de Itens no Inventário (Apenas se for um Jogador)
        if (entity instanceof Player player) {
            int goldCount = countGoldNuggets(player);

            // Se tiver 20 ou mais: Leva Dano (1 coração a cada 20 ticks / 1 segundo)
            if (goldCount >= 20) {
                if (player.tickCount % 20 == 0) {
                    player.hurt(player.damageSources().magic(), 2.0F);
                }
            }

            // Se tiver 10 ou mais: Fica Cego
            if (goldCount >= 10) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false));
            }

            // Se tiver 5 ou mais: Náusea
            else if (goldCount >= 5) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false));
            }
        }
    }

    // Função auxiliar para contar as pepitas de ouro
    private int countGoldNuggets(Player player) {
        int total = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == ModItems.CURSED_PORKCHOP.get()) {
                total += stack.getCount();
            }
        }
        return total;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Retorna true para o efeito ser aplicado em todos os ticks
        return true;
    }


}
