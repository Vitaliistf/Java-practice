package com.epam.rd.autotasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialRegularInputTesting {

    Factorial factorial = new Factorial();

    @Test
    void testRegularInputCases() {
        assertEquals("6227020800", factorial.factorial("13"));
        assertEquals("87178291200", factorial.factorial("14"));
        assertEquals("1307674368000", factorial.factorial("15"));
    }
}
