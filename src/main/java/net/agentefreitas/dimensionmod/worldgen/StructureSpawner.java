package net.agentefreitas.dimensionmod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.server.level.TicketType;

import java.util.Optional;

public class StructureSpawner {

    public static void generate(ServerLevel world, BlockPos pos, ResourceLocation structureId) {

        // Força o carregamento do chunk onde a estrutura será gerada
        ChunkPos chunkPos = new ChunkPos(pos);
        world.getChunkSource().addRegionTicket(TicketType.START, chunkPos, 1, Unit.INSTANCE.INSTANCE); // 👈 Usa Unit.INSTANCE

        // Obtém a estrutura do StructureManager
        StructureTemplateManager structureManager = world.getStructureManager(); // 👈 world, não serverLevel
        Optional<StructureTemplate> optionalTemplate = structureManager.get(structureId);

        // Verifica se a estrutura foi carregada com sucesso
        if (optionalTemplate.isEmpty()) {
            System.out.println("❌ Falha ao carregar estrutura: " + structureId);
            return;
        }

        StructureTemplate template = optionalTemplate.get();

        // Configurações para colocar a estrutura
        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setMirror(Mirror.NONE)
                .setRotation(Rotation.NONE)
                .setIgnoreEntities(false)
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

        // Coloca a estrutura no mundo
        template.placeInWorld(world, pos, pos, settings, world.getRandom(), 2);

        System.out.println("✅ Estrutura gerada em: " + pos);
    }

}