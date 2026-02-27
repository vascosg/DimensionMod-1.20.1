package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RainbowNameItem extends Item {
    public RainbowNameItem(Properties pProperties) {
        super(pProperties);
    }

    public Component getName(ItemStack pStack) {
        // Em vez de uma String fixa, podes ir buscar o nome que está no teu lang
        // Isso permite que o nome seja traduzível mas ganhe as cores
        String name = Component.translatable(this.getDescriptionId()).getString();

        MutableComponent finalName = Component.empty();
        long time = System.currentTimeMillis() / 80; // Velocidade da animação

        for (int i = 0; i < name.length(); i++) {
            float hue = ((time + (i * 2)) % 100) / 100f; // Ajuste para um arco-íris mais suave
            int color = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);

            finalName.append(Component.literal(String.valueOf(name.charAt(i)))
                    .withStyle(style -> style.withColor(color)));
        }
        return finalName;
    }
}
