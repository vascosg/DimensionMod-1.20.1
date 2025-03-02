package net.agentefreitas.dimensionmod.item.custom;

import net.agentefreitas.dimensionmod.screen.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;


public class DimensionBookItem extends WritableBookItem {
    public DimensionBookItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (pLevel.isClientSide) {

            ItemStack stack = pPlayer.getItemInHand(pHand);

            Minecraft.getInstance().setScreen(new BookScreen(pPlayer,stack,pHand));
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
    }

    @Override
    public boolean canBeDepleted() {
        return false; // Para impedir que o item se quebre
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag(); // Faz o item brilhar se tiver NBT
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        return stack.getTag(); // Permite o NBT ser transmitido corretamente
    }

    public void setShareTag(ItemStack stack, CompoundTag tag) {
        stack.setTag(tag); // Salva o NBT quando o livro for colocado no bloco
    }
}
