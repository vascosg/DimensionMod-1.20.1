package net.agentefreitas.dimensionmod.entity;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.BaiYuEntity;
import net.agentefreitas.dimensionmod.entity.custom.DiscipleEntity;
import net.agentefreitas.dimensionmod.entity.custom.LittlePurpleEntity;
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

    public static final RegistryObject<EntityType<LittlePurpleEntity>> LITTLE_PURPLE =
            ENTITY_TYPES.register("little_purple", () -> EntityType.Builder.of(LittlePurpleEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 1.8f).build("little_purple"));

    public static final RegistryObject<EntityType<BaiYuEntity>> BAI_YU =
            ENTITY_TYPES.register("bai_yu", () -> EntityType.Builder.of(BaiYuEntity::new, MobCategory.AMBIENT)
                    .sized(0.6f, 2.0f).build("bai_yu"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
