package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.*;
import net.agentefreitas.dimensionmod.entity.client.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.agentefreitas.dimensionmod.entity.MobEntitySpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DimensionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    //Disciple
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DISCIPLE.get(), DiscipleEntity.createAttributes().build());
        event.put(ModEntities.LITTLE_PURPLE.get(), LittlePurpleEntity.createAttributes().build());
        event.put(ModEntities.BAI_YU.get(), BaiYuEntity.createAttributes().build());
        event.put(ModEntities.GAO_YU.get(), GaoYuEntity.createAttributes().build());
        event.put(ModEntities.DEMI_CAT.get(), DemiCatEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_FRUIT.get(), OrangeFruitEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_VILLAGER.get(),OrangeVillagerEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_VILLAGER_RARE.get(),OrangeVillagerRareEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_VILLAGER_JUICE.get(),OrangeVillagerJuiceEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_VILLAGER_MAGE.get(),OrangeVillagerMageEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_PIG.get(),OrangePigEntity.createAttributes().build());
        event.put(ModEntities.GARNET_SWORD.get(),GarnetSwordEntity.createAttributes().build());
        event.put(ModEntities.AQUAMARINE_SWORD.get(),AquamarineSwordEntity.createAttributes().build());
        event.put(ModEntities.PERIDOT_SWORD.get(),PeridotSwordEntity.createAttributes().build());
        event.put(ModEntities.CITRINE_SWORD.get(),CitrineSwordEntity.createAttributes().build());
        event.put(ModEntities.AMBER_SWORD.get(),AmberSwordEntity.createAttributes().build());
        event.put(ModEntities.KUNZITE_SWORD.get(),KunziteSwordEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_ZORD.get(),KunziteSwordEntity.createAttributes().build());
    }


    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.DISCIPLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkDiscipleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.LITTLE_PURPLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.BAI_YU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.GAO_YU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.DEMI_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_VILLAGER_RARE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_VILLAGER_JUICE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_VILLAGER_MAGE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.GARNET_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.AQUAMARINE_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.PERIDOT_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.CITRINE_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.AMBER_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.KUNZITE_SWORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ORANGE_ZORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MobEntitySpawnPlacements::checkLittlePrupleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);


    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.AMBER_PROJECTILE.get(), AmberProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.KUNZITE_PROJECTILE.get(), KunziteProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.ORANGE_ZORD_FACTORY.get(), OrangeZordFactoryRenderer::new);
    }

}
