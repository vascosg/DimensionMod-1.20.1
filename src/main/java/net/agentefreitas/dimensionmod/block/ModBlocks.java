package net.agentefreitas.dimensionmod.block;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.custom.DimensionBookLectern;
import net.agentefreitas.dimensionmod.block.custom.GuardianBlock;
import net.agentefreitas.dimensionmod.block.custom.GuardianDoorBlock;
import net.agentefreitas.dimensionmod.block.custom.IceCropBlock;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DimensionMod.MOD_ID);


    public static final RegistryObject<Block> DIMENSION_DOOR_BLOCK = registerBlock("dimension_door_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> DIMENSION_BOOK_LECTERN = registerBlock("lectern_white",
            () -> new DimensionBookLectern(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> GUARDIAN_BLOCK = registerBlock("guardian_block",
            ()-> new GuardianBlock(BlockBehaviour.Properties.copy((Blocks.BEDROCK))));

    public static final RegistryObject<Block> GUARDIAN_DOOR_BLOCK = registerBlock("guardian_door_block",
            ()-> new GuardianDoorBlock(BlockBehaviour.Properties.copy((Blocks.BEDROCK))));

    public static final RegistryObject<Block> ICY_CROP = BLOCKS.register("icy_crop",
            () -> new IceCropBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).noOcclusion().noCollission()));

    /** exemplo
    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK = registerBlock("raw_sapphire_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> NETHER_SAPPHIRE_ORE = registerBlock("nether_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .strength(1f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> END_STONE_SAPPHIRE_ORE = registerBlock("end_stone_sapphire_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)
                    .strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    **/


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}