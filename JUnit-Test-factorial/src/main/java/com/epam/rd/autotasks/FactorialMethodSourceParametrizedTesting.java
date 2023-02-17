package com.epam.rd.autotasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialMethodSourceParametrizedTesting {

    Factorial factorial = new Factorial();

    @ParameterizedTest
    @MethodSource("testCases")
    void testFactorial(String in, String expected) {
        assertEquals(expected, factorial.factorial(in));
    }

    static Stream<Arguments> testCases() {
        return Stream.of(new String[]{"1", "1"},
                        new String[]{"2", "2"},
                        new String[]{"5", "120"})
                .map(Arguments::of);
    }
}
