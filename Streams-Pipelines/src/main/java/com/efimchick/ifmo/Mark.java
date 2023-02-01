package com.efimchick.ifmo;

import java.util.stream.Stream;

public enum Mark {
    A(91, 100),
    B(83, 91),
    C(75, 83),
    D(68, 75),
    E(60, 68),
    F(0, 60);

    private final int min;
    private final int max;

    Mark(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static String getMark(double markValue) {
        return Stream.of(Mark.values())
                .filter(x -> markValue <= x.max && markValue >= x.min)
                .findFirst()
                .orElse(F)
                .toString();
    }
}