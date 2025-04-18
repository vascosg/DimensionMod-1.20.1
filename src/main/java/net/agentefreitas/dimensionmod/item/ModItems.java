package net.agentefreitas.dimensionmod.item;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.item.custom.DimensionBookItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DimensionMod.MOD_ID);

    public static final RegistryObject<Item> DIMENSION_DOOR_KEY = ITEMS.register("dimension_door_key",
            () -> new Item(new Item.Properties()));
    //Books
    public static final RegistryObject<Item> DIARY_OF_A_CREATOR = ITEMS.register("diary_of_a_creator",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLUE_BOOK = ITEMS.register("blue_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GREEN_BOOK = ITEMS.register("green_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ORANGE_BOOK = ITEMS.register("orange_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURPLE_BOOK = ITEMS.register("purple_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RED_BOOK = ITEMS.register("red_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> YELLOW_BOOK = ITEMS.register("yellow_book",
            () -> new DimensionBookItem(new Item.Properties().stacksTo(1)));


    /** exemplo
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));
    **/

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}