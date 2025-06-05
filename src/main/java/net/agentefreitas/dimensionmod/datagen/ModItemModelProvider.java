package net.agentefreitas.dimensionmod.datagen;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DimensionMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleItem(ModItems.DIMENSION_DOOR_KEY);

        simpleItem(ModItems.DIARY_OF_A_CREATOR);
        simpleItem(ModItems.BLUE_BOOK);
        simpleItem(ModItems.GREEN_BOOK);
        simpleItem(ModItems.ORANGE_BOOK);
        simpleItem(ModItems.PURPLE_BOOK);
        simpleItem(ModItems.RED_BOOK);
        simpleItem(ModItems.YELLOW_BOOK);

        handheldItem(ModItems.FIRE_TREASURE);
        handheldItem(ModItems.WATER_TREASURE);
        handheldItem(ModItems.WOOD_TREASURE);
        handheldItem(ModItems.EARTH_TREASURE);
        handheldItem(ModItems.GOLD_TREASURE);

        handheldItem(ModItems.FIVE_ELEMENT_INDESTRUCTIBLE_SWORD);

        withExistingParent(ModItems.DISCIPLE_SPANW_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));


        /** exemplo
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.RAW_SAPPHIRE);

        simpleItem(ModItems.METAL_DETECTOR);
        simpleItem(ModItems.PINE_CONE);
        simpleItem(ModItems.STRAWBERRY);
         **/
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DimensionMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DimensionMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(DimensionMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}