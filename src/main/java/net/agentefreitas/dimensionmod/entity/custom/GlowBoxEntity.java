package net.agentefreitas.dimensionmod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.Collections;

public class GlowBoxEntity extends LivingEntity {

    public GlowBoxEntity(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.setInvulnerable(true);
    }

    // Obrigatório para o Forge não crashar ao spawnar
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D);
    }

    @Override public Iterable<ItemStack> getArmorSlots() { return Collections.emptyList(); }
    @Override public ItemStack getItemBySlot(EquipmentSlot slot) { return ItemStack.EMPTY; }

    @Override public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}
    @Override public HumanoidArm getMainArm() { return HumanoidArm.RIGHT; }

    // Garante que o brilho está sempre ativo no código
    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        // Apenas fazemos a verificação no lado do servidor
        if (!this.level().isClientSide) {
            // Pega na posição atual da entidade (arredondada para BlockPos)
            BlockPos pos = this.blockPosition();

            // Verifica se o bloco naquela posição é AR (ou qualquer bloco que não seja o que queres)
            // Podes personalizar para verificar se o bloco é especificamente o teu "Glow Block"
            if (this.level().isEmptyBlock(pos)) {
                // Se o bloco desapareceu, removemos a entidade
                this.discard();
            }
        }
    }
}