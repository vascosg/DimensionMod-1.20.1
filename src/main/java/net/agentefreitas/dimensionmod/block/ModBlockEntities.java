package net.agentefreitas.dimensionmod.block;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.block.custom.DimensionBookLecternBlockEntity;
import net.agentefreitas.dimensionmod.block.custom.GuardianBlockEntity;
import net.agentefreitas.dimensionmod.block.custom.GuardianDoorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DimensionMod.MOD_ID);

    // Registra o BlockEntity
    public static final RegistryObject<BlockEntityType<DimensionBookLecternBlockEntity>> DIMENSION_BOOK_LECTERN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dimension_book_lectern",
                    () -> BlockEntityType.Builder.of(DimensionBookLecternBlockEntity::new, ModBlocks.DIMENSION_BOOK_LECTERN.get()).build(null));

    public static final RegistryObject<BlockEntityType<GuardianBlockEntity>> GUARDIAN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("guardian_block_entity",
                    () -> BlockEntityType.Builder.of(GuardianBlockEntity::new, ModBlocks.GUARDIAN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<GuardianDoorBlockEntity>> GUARDIAN_DOOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("guardian_door_block_entity",
                    () -> BlockEntityType.Builder.of(GuardianDoorBlockEntity::new, ModBlocks.GUARDIAN_DOOR_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}