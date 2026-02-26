package net.agentefreitas.dimensionmod.item;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.item.custom.*;
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


    //weapon
    public static final RegistryObject<Item> FIVE_ELEMENT_INDESTRUCTIBLE_SWORD = ITEMS.register("five_element_indestructible_sword",
            () -> new SwordItem(ModToolTiers.FIVE_ELEMENT_TIER, 1,-1f, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> SUN_SWORD = ITEMS.register("sun_sword",
            ()-> new SunSwordItem(ModToolTiers.SUN_STONE_TIER, 3, -2.4F, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> MOON_BOW = ITEMS.register("moon_bow",
            ()-> new MoonBowItem(new Item.Properties().durability(1500)));

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
    public static final RegistryObject<Item> ORANGE_VILLAGER_SPANW_EGG = ITEMS.register("orange_villager_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORANGE_VILLAGER, 0Xe97b13, 0xffc47a, new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_VILLAGER_RARE_SPANW_EGG = ITEMS.register("orange_villager_rare_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORANGE_VILLAGER_RARE, 0Xe97b13, 0x8f0e0e, new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_VILLAGER_JUICE_SPANW_EGG = ITEMS.register("orange_villager_juice_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORANGE_VILLAGER_JUICE, 0Xe97b13, 0xcacaca, new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_VILLAGER_MAGE_SPANW_EGG = ITEMS.register("orange_villager_mage_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORANGE_VILLAGER_MAGE, 0Xe97b13, 0x273f2c, new Item.Properties()));



    //armor
    public static final RegistryObject<Item> COLDEST_ICE = ITEMS.register("coldest_ice",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COLDEST_ICE_HELMET = ITEMS.register("coldest_ice_helmet",
            () -> new ArmorItem(ModArmorMaterials.COLDEST_ICE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COLDEST_ICE_CHESTPLATE = ITEMS.register("coldest_ice_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COLDEST_ICE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> COLDEST_ICE_LEGGINGS = ITEMS.register("coldest_ice_leggings",
            () -> new ArmorItem(ModArmorMaterials.COLDEST_ICE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> COLDEST_ICE_BOOTS = ITEMS.register("coldest_ice_boots",
            () -> new ArmorItem(ModArmorMaterials.COLDEST_ICE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ICY_SEEDS = ITEMS.register("icy_seeds",
            () -> new ItemNameBlockItem(ModBlocks.ICY_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> ICY_CRYSTAL = ITEMS.register("icy_crystal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> OverworldBailer = ITEMS.register("overworld_bailer",
            () -> new OverworldBailerItem(new Item.Properties().fireResistant()));


    public static final RegistryObject<Item> ZORD_MAP = ITEMS.register("zord_map",
            () -> new ZordMapItem( new Item.Properties()));

    public static final RegistryObject<Item> ZORD_BP_HEAD = ITEMS.register("zord_bp_head",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZORD_BP_CHEST = ITEMS.register("zord_bp_chest",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZORD_BP_R_ARM = ITEMS.register("zord_bp_r_arm",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZORD_BP_L_ARM = ITEMS.register("zord_bp_l_arm",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZORD_BP_R_LEG = ITEMS.register("zord_bp_r_leg",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZORD_BP_L_LEG = ITEMS.register("zord_bp_l_leg",
            () -> new Item(new Item.Properties()));

    //others
    public static final RegistryObject<Item> SPACE_STAFF = ITEMS.register("space_staff",
            () -> new SpaceStaffItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BROKEN_SPACE_STAFF = ITEMS.register("broken_space_staff",
            () -> new Item(new Item.Properties().stacksTo(1)));

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