package net.agentefreitas.dimensionmod.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum DemiCatVariant {
    MANONE(0),
    MANTWO(1),
    WOMANONE(2),
    WOMANTWO(3);

    private static final DemiCatVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(DemiCatVariant::getId)).toArray(DemiCatVariant[]::new);
    private final int id;

    DemiCatVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DemiCatVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
