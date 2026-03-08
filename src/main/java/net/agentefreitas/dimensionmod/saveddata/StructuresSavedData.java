package net.agentefreitas.dimensionmod.saveddata;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StructuresSavedData extends SavedData {
    private final List<BlockPos> structures = new ArrayList<>();

    // Adiciona uma nova estrutura à lista
    public void addStructure(BlockPos pos) {
        if (!structures.contains(pos)) {
            structures.add(pos);
            this.setDirty(); // Avisa o Minecraft que tem de salvar no disco
        }
    }

    // Remove (caso o bloco central seja destruído)
    public void removeStructure(BlockPos pos) {
        if (structures.remove(pos)) {
            this.setDirty();
        }
    }

    // Encontra a estrutura mais próxima do jogador
    public BlockPos getNearest(BlockPos playerPos) {
        return structures.stream()
                .min(Comparator.comparingDouble(p -> p.distSqr(playerPos)))
                .orElse(null);
    }

    // --- LÓGICA DE SALVAR E CARREGAR (NBT) ---

    public static StructuresSavedData load(CompoundTag nbt) {
        StructuresSavedData data = new StructuresSavedData();
        ListTag list = nbt.getList("Positions", Tag.TAG_LONG);
        for (int i = 0; i < list.size(); i++) {
            data.structures.add(BlockPos.of(((LongTag) list.get(i)).getAsLong()));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (BlockPos pos : structures) {
            list.add(LongTag.valueOf(pos.asLong()));
        }
        nbt.put("Positions", list);
        return nbt;
    }

    // Método estático para aceder aos dados a partir de qualquer lugar no servidor
    public static StructuresSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                StructuresSavedData::load,
                StructuresSavedData::new,
                "dimensionmod_structures"
        );
    }
}
