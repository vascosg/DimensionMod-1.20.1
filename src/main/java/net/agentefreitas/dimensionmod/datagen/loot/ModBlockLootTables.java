package net.agentefreitas.dimensionmod.datagen.loot;

import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.block.custom.IceCropBlock;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {


        this.dropSelf(ModBlocks.DIMENSION_DOOR_BLOCK.get());
        this.dropSelf(ModBlocks.DIMENSION_BOOK_LECTERN.get());
        this.dropSelf(ModBlocks.GUARDIAN_BLOCK.get());
        this.dropSelf(ModBlocks.GUARDIAN_DOOR_BLOCK.get());

        this.dropSelf(ModBlocks.ORANGE_LOG.get());
        this.dropSelf(ModBlocks.ORANGE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ORANGE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ORANGE_WOOD.get());
        this.dropSelf(ModBlocks.ORANGE_PLANKS.get());
        this.dropSelf(ModBlocks.ORANGE_LEAVES.get());
        this.dropSelf(ModBlocks.ORANGE_FRUIT_LEAF.get());
        this.dropSelf(ModBlocks.ORANGE_SAPLING.get());

        this.dropSelf(ModBlocks.ORANGE_BERRY_BUSH.get());
        this.dropSelf(ModBlocks.TERRACOTTA_LEAVES.get());
        this.dropSelf(ModBlocks.ORANGE_MUSHROOM_BLOCK.get());

        this.dropSelf(ModBlocks.ORANGE_TERRACOTTA_STAIRS.get());
        this.dropSelf(ModBlocks.ORANGE_TERRACOTTA_SLAB.get());

        this.dropSelf(ModBlocks.WHITE_TERRACOTTA_STAIRS.get());
        this.add(ModBlocks.WHITE_TERRACOTTA_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WHITE_TERRACOTTA_SLAB.get()));

        this.dropSelf((ModBlocks.PO_TERRACOTTA.get()));

        this.dropSelf(ModBlocks.AMBER_BLOCK.get());
        this.dropSelf(ModBlocks.AQUAMARINE_BLOCK.get());
        this.dropSelf(ModBlocks.CITRINE_BLOCK.get());
        this.dropSelf(ModBlocks.GARNET_BLOCK.get());
        this.dropSelf(ModBlocks.KUNZITE_BLOCK.get());
        this.dropSelf(ModBlocks.PERIDOT_BLOCK.get());

        /** exemplo
        this.dropSelf(ModBlocks.RAW_SAPPHIRE_BLOCK.get());
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());

        this.add(ModBlocks.SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.NETHER_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.END_STONE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        **/

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.ICY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(IceCropBlock.AGE, 6));

        this.add(ModBlocks.ICY_CROP.get(), createCropDrops(ModBlocks.ICY_CROP.get(), ModItems.ICY_CRYSTAL.get(),
                ModItems.ICY_SEEDS.get(), lootitemcondition$builder));

        this.add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.SAPPHIRE.get()));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}