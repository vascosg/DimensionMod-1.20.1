package net.agentefreitas.dimensionmod.function.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.agentefreitas.dimensionmod.function.ModFunctions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class RandomAnyItemFunction extends LootItemConditionalFunction {
    protected RandomAnyItemFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public LootItemFunctionType getType() {
        return ModFunctions.RANDOM_ANY_ITEM.get();
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        var registry = BuiltInRegistries.ITEM;
        int count = registry.size();

        int randomIndex = context.getRandom().nextInt(count);
        Item randomItem = registry.byId(randomIndex);


        return new ItemStack(randomItem);
    }

    // --- O SERIALIZER CORRETO PARA A TUA VERSÃO ---
    public static class Serializer extends LootItemConditionalFunction.Serializer<RandomAnyItemFunction> {
        @Override
        public RandomAnyItemFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new RandomAnyItemFunction(conditions);
        }

        // O Forge/Minecraft exige este método para saber como escrever no JSON (pode ficar vazio)
        @Override
        public void serialize(JsonObject json, RandomAnyItemFunction value, JsonSerializationContext context) {
            super.serialize(json, value, context);
        }
    }
}