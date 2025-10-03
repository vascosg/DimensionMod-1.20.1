package net.agentefreitas.dimensionmod.entity;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.custom.*;
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
                    .sized(0.6f, 1.9f).build("bai_yu"));

    public static final RegistryObject<EntityType<GaoYuEntity>> GAO_YU =
            ENTITY_TYPES.register("gao_yu", () -> EntityType.Builder.of(GaoYuEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 1.8f).build("gao_yu"));

    public static final RegistryObject<EntityType<DemiCatEntity>> DEMI_CAT =
            ENTITY_TYPES.register("demi_cat", () -> EntityType.Builder.of(DemiCatEntity::new, MobCategory.AMBIENT)
                    .sized(0.6f, 1.9f).build("demi_cat"));

    public static final RegistryObject<EntityType<OrangeFruitEntity>> ORANGE_FRUIT =
            ENTITY_TYPES.register("orange_fruit", () -> EntityType.Builder.of(OrangeFruitEntity::new, MobCategory.AMBIENT)
                    .sized(0.3f, 0.3f).build("orange_fruit"));

    public static final RegistryObject<EntityType<OrangeVillagerEntity>> ORANGE_VILLAGER =
            ENTITY_TYPES.register("orange_villager", () -> EntityType.Builder.of(OrangeVillagerEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f).build("orange_villager"));

    public static final RegistryObject<EntityType<OrangeVillagerRareEntity>> ORANGE_VILLAGER_RARE =
            ENTITY_TYPES.register("orange_villager_rare", () -> EntityType.Builder.of(OrangeVillagerRareEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f).build("orange_villager_rare"));

    public static final RegistryObject<EntityType<OrangeVillagerJuiceEntity>> ORANGE_VILLAGER_JUICE =
            ENTITY_TYPES.register("orange_villager_juice", () -> EntityType.Builder.of(OrangeVillagerJuiceEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f).build("orange_villager_juice"));

    public static final RegistryObject<EntityType<OrangeVillagerMageEntity>> ORANGE_VILLAGER_MAGE =
            ENTITY_TYPES.register("orange_villager_mage", () -> EntityType.Builder.of(OrangeVillagerMageEntity::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f).build("orange_villager_mage"));

    public static final RegistryObject<EntityType<OrangePigEntity>> ORANGE_PIG =
            ENTITY_TYPES.register("orange_pig", () -> EntityType.Builder.of(OrangePigEntity::new, MobCategory.AMBIENT)
                    .sized(0.6f, 1.9f).build("orange_pig"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
