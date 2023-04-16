package com.epam.rd.qa.classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.epam.rd.qa.classes.Util.nextDouble;
import static org.junit.jupiter.api.Assertions.*;

class ArrayRectanglesTest {

    public static Stream<Arguments> casesNumberMaxArea() {
        Random r = new Random(392);
        Iterator<Integer> itMaxArea = List.of(2, 1, 0, 8, 0).iterator();
        Iterator<Integer> itMinPerimeter = List.of(3, 3, 1, 4, 8).iterator();
        Iterator<Integer> itNumSquare = List.of(1, 3, 1, 7, 2).iterator();
        Collection<ArrayRectangles> arrayRectangles = IntStream.iterate(0, x -> x + 1).limit(5)
                .mapToObj(x ->
                        new ArrayRectangles(
                                IntStream.iterate(0, y -> y + 1).limit(r.nextInt(10) + 1)
                                        .mapToObj(y -> r.nextInt(75) % 2 == 0 ?
                                                new Rectangle(nextDouble(r, 1., 5), nextDouble(r, 1., 5.)) :
                                                new Rectangle(nextDouble(r, 1., 5.)))
                                        .toArray(Rectangle[]::new)))
                .collect(Collectors.toList());
        return arrayRectangles.stream().map(rect -> Arguments.of(rect, itMaxArea.next(), itMinPerimeter.next(), itNumSquare.next()));
    }

    @Test
    void testConstructorShouldCreate() {
        assertDoesNotThrow(() -> new ArrayRectangles(1));
        assertEquals(0, new ArrayRectangles(1).size(),
                "This constructor creates empty array with a given length");
        assertEquals(1, new ArrayRectangles(new Rectangle()).size(),
                "This constructor creates array with given elements or array");
        assertEquals(2, new ArrayRectangles(new Rectangle(), new Rectangle(3.)).size(),
                "This constructor creates array with given elements or array");
        Rectangle[] rectangles = {
                new Rectangle(), new Rectangle(1.), new Rectangle(3., 2.)
        };
        assertEquals(3, new ArrayRectangles(rectangles).size(),
                "This constructor creates array with given elements or array");
    }

    @Test
    void testConstructorShouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new ArrayRectangles(-1),
                "The size of internal array must not be negative or equal to zero");
        assertThrows(IllegalArgumentException.class,
                () -> new ArrayRectangles(0),
                "The size of internal array must not be negative or equal to zero");
        Rectangle[] rectangles = null;
        assertThrows(IllegalArgumentException.class,
                () -> new ArrayRectangles(rectangles),
                "An array of Rectangle must be provided");
        assertThrows(IllegalArgumentException.class,
                ArrayRectangles::new,
                "An array of Rectangle must be provided");
    }

    @Test
    void testAddRectangle() {
        ArrayRectangles rectangles = new ArrayRectangles(3);
        assertTrue(rectangles.addRectangle(new Rectangle()));
        assertEquals(1, rectangles.size(),
                "Should return actual amount of elements in the container");
        assertTrue(rectangles.addRectangle(new Rectangle()));
        assertEquals(2, rectangles.size(),
                "Should return actual amount of elements in the container");
        assertTrue(rectangles.addRectangle(new Rectangle()));
        assertEquals(3, rectangles.size(),
                "Should return actual amount of elements in the container");
        assertFalse(rectangles.addRectangle(new Rectangle()));
        assertEquals(3, rectangles.size(),
                "Should return actual amount of elements in the container");
    }

    @ParameterizedTest
    @MethodSource("casesNumberMaxArea")
    void testNumberMaxArea(ArrayRectangles rectangles,
                           int expectedMaxArea, int expectedMinPerimeter,
                           int expectedSquares) {
        assertEquals(expectedMaxArea, rectangles.indexMaxArea());
        assertEquals(expectedMinPerimeter, rectangles.indexMinPerimeter());
        assertEquals(expectedSquares, rectangles.numberSquares());
    }
}