package net.agentefreitas.dimensionmod.item.custom;

import net.agentefreitas.dimensionmod.screen.ZordRecipesBookScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.property.Properties;

public class ZordRecipesBookItem extends Item {

    public ZordRecipesBookItem(Properties props) {
        super(props.stacksTo(1)); // O livro não agrupa (stack)
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            // No Minecraft, interfaces (Screens) só existem no Cliente
            openGui();
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    // Método para abrir a interface
    private void openGui() {
        net.minecraft.client.Minecraft.getInstance().setScreen(new ZordRecipesBookScreen());
    }
}
