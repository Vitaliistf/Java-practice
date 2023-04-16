package com.epam.rd.autotasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class QuadraticEquationTwoRootsCasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();

    private final double a;
    private final double b;
    private final double c;
    private final String expected;

    public QuadraticEquationTwoRootsCasesTesting(double a, double b, double c, String expected) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
                {1, -2, -3, "3.0 -1.0"},
                {1, -45, 324, "36.0 9.0"},
                {2, -5, 3, "1.5 1.0"},
                {1, 4, -5, "1.0 -5.0"}
        });
    }

    @Test
    public void testTwoRootsCase() {
        String result = quadraticEquation.solve(a, b, c);
        if (!result.contains(" ") || result.equals("no roots")) {
            throw new AssertionError();
        }
        String[] actual = result.split(" ");
        assertTrue(expected.contains(actual[0]));
        assertTrue(expected.contains(actual[1]));
    }

}
