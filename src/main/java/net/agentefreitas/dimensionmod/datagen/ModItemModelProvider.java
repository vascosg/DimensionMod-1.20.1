package net.agentefreitas.dimensionmod.datagen;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
}