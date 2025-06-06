package net.agentefreitas.dimensionmod.entity;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DimensionMod.MOD_ID);


    public static final RegistryObject<EntityType<DiscipleEntity>> DISCIPLE =
            ENTITY_TYPES.register("disciple", () -> EntityType.Builder.of(DiscipleEntity::new, MobCategory.AMBIENT)
                    .sized(0.6f, 2.1f).build("disciple"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
