package com.epam.rd.autotasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class QuadraticEquationZeroACasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();

    private final double a;
    private final double b;
    private final double c;

    public QuadraticEquationZeroACasesTesting(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
                {0, -2, -3},
                {0, -45, 324},
                {0, -5, 3},
                {0, 4, -5}
        });
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTwoRootsCase() {
        quadraticEquation.solve(a, b, c);
    }
}
