package net.agentefreitas.dimensionmod.function;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.function.loot.RandomAnyItemFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, DimensionMod.MOD_ID);

    // Agora passamos o CODEC da classe que criámos acima
    public static final RegistryObject<LootItemFunctionType> RANDOM_ANY_ITEM =
            LOOT_FUNCTIONS.register("random_any_item", () -> new LootItemFunctionType(new RandomAnyItemFunction.Serializer()));

    public static void register(IEventBus eventBus) {
        LOOT_FUNCTIONS.register(eventBus);
    }
}