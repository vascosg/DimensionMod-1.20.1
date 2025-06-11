package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.agentefreitas.dimensionmod.entity.MobEntitySpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
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
    }

}
