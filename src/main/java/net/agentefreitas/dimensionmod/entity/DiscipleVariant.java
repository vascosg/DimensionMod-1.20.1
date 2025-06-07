package net.agentefreitas.dimensionmod.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum DiscipleVariant {
    MAN(0),
    WOMAN(1);

    private static final DiscipleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(DiscipleVariant::getId)).toArray(DiscipleVariant[]::new);
    private final int id;

    DiscipleVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DiscipleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}