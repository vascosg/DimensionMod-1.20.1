package net.agentefreitas.dimensionmod.item;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, DimensionMod.MOD_ID);

    // 2x2 blocos = 32x32 px de textura (múltiplos de 16)
    public static final RegistryObject<PaintingVariant> HAPPY_ORANGES =
            PAINTINGS.register("happy_oranges", () -> new PaintingVariant(16, 32));

    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }
}
