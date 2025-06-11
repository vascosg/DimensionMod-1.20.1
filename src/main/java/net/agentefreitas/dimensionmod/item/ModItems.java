package net.agentefreitas.dimensionmod.item;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.item.custom.BlackWordlessBook;
import net.agentefreitas.dimensionmod.item.custom.DimensionBookItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
            () -> new BlackWordlessBook(new Item.Properties().stacksTo(1)));
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

    //Treasures
    public static final RegistryObject<Item> FIRE_TREASURE = ITEMS.register("fire_treasure",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WATER_TREASURE = ITEMS.register("water_treasure",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WOOD_TREASURE = ITEMS.register("wood_treasure",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EARTH_TREASURE = ITEMS.register("earth_treasure",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLD_TREASURE = ITEMS.register("gold_treasure",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FIVE_ELEMENT_INDESTRUCTIBLE_SWORD = ITEMS.register("five_element_indestructible_sword",
            () -> new SwordItem(ModToolTiers.FIVE_ELEMENT_TIER, 1,-1f, new Item.Properties().fireResistant()));


    //Entities
    public static final RegistryObject<Item> DISCIPLE_SPANW_EGG = ITEMS.register("disciple_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DISCIPLE, 0xf2f2f2, 0x990000, new Item.Properties()));

    public static final RegistryObject<Item> LITTLE_PURPLE_SPANW_EGG = ITEMS.register("little_purple_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LITTLE_PURPLE, 0xf2f2f2, 0x8b4eb4, new Item.Properties()));

    public static final RegistryObject<Item> BAI_YU_SPANW_EGG = ITEMS.register("bai_yu_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BAI_YU, 0xf2f2f2, 0x7db473, new Item.Properties()));

    public static final RegistryObject<Item> GAO_YU_SPANW_EGG = ITEMS.register("gao_yu_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GAO_YU, 0xf2f2f2, 0x7a1d1d, new Item.Properties()));

    public static final RegistryObject<Item> DEMI_CAT_SPANW_EGG = ITEMS.register("demi_cat_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DEMI_CAT, 0xf2f2f2, 0x27273a, new Item.Properties()));


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