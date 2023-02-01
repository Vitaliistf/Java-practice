package com.epam.rd.qa.classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    public static Stream<Arguments> casesArea() {
        Random r = new Random(4253);
        Collection<Double> areas = List.of(158.24662905869582, 2111.0069438480537, 1775.40415738466,
                247.86579723139246, 542.7785703576241, 30.189667911463868, 734.776370503197,
                840.838478420113, 577.7086094636766, 219.86998143192577, 186.36755501881183,
                1029.1701890619092, 884.517838045522, 860.3089527461944, 41.232968124872464,
                418.5253596464766, 1990.3790659430886, 1321.4691946172259, 1578.4431197673628,
                941.8273454755961);
        Collection<Double> perimeters = List.of(68.62642497093255, 184.29624336231012, 170.7349372352827,
                109.88261766114445, 94.63115678683027, 66.81372460818928, 119.74007031794233,
                121.81974074613115, 97.01201346510645, 65.80812028914265, 55.37329639001775,
                134.19302449644894, 120.82363869480912, 117.5005901487308, 91.33082130038213,
                82.2662419174099, 179.38438080126707, 145.46629347146683, 160.12055618410147,
                123.37058515171978
        );
        Iterator<Double> ita = areas.iterator();
        Iterator<Double> itp = perimeters.iterator();
        return IntStream.iterate(0, x -> x + 1).limit(20).mapToObj(
                i -> Arguments.of(
                        Util.nextDouble(r, .01, 50.), Util.nextDouble(r, .01, 50.), ita.next(), itp.next()
                )
        );
    }

    public static Stream<Arguments> casesHashCodeEquals() {
        Random r = new Random(672);
        return IntStream.iterate(0, x -> x + 1).limit(5)
                .mapToObj(i -> Arguments.of(Util.nextDouble(r, 1., 5.), Util.nextDouble(r, 1., 5.)));
    }

    @Test
    void testCompliance() {
        Class<Rectangle> rectangleClass = Rectangle.class;
        Field[] declaredFields = rectangleClass.getDeclaredFields();
        assertEquals(2, Arrays.stream(declaredFields)
                .filter(f -> f.getName().equals("sideA")
                        || f.getName().equals("sideB"))
                .filter(f -> Modifier.isPrivate(f.getModifiers())
                        && !Modifier.isStatic(f.getModifiers()))
                .count(),
                "The fields to store sides' length should not be " +
                        "public and static");
        Method[] declaredMethods = rectangleClass.getDeclaredMethods();
        assertEquals(0,Arrays.stream(declaredMethods)
                .filter(m -> m.getName().equals("setSideA"))
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .count(),
                "You should not declare public methods to mutate sides");
        assertEquals(0,Arrays.stream(declaredMethods)
                .filter(m -> m.getName().equals("setSideB"))
                .count(),
                "You should not declare public methods to mutate sides");
    }

    @Test
    void testConstructorShouldCreate() {
        Rectangle r = new Rectangle();
        assertEquals(4., r.getSideA(), .000000001,
                "Default length of sideA is 4.0");
        assertEquals(3., r.getSideB(),
                "Default length of sideB is 3.0");
        r = new Rectangle(2.);
        assertEquals(2., r.getSideA(), 0.00000001,
                "The length of sideA is 2.0");
        assertEquals(r.getSideA(), r.getSideB(), 0.00000001,
                "The length of sideB should be equal sideB");
        r = new Rectangle(2., 1000.);
        assertEquals(2., r.getSideA(), 0.00000001);
        assertEquals(1000., r.getSideB(), 0.00000001);
        r = new Rectangle(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, r.getSideA());
        assertEquals(Double.MAX_VALUE, r.getSideB());
    }

    @Test
    void testConstructorShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(-0.00000001),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(-0.00000001, -0.0000001),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0., 0.),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(Double.MIN_VALUE, -Double.MIN_VALUE),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(-Double.MIN_VALUE, Double.MIN_VALUE),
                "The IllegalArgumentException should be thrown " +
                        "if length of sides less or equal to zero");

    }

    @ParameterizedTest
    @MethodSource("casesArea")
    void testArea(double a, double b, double expArea, double expPerimeter) {
        Rectangle r = new Rectangle(a, b);
        assertEquals(r.area(), expArea, 0.0000001);
        assertEquals(r.perimeter(), expPerimeter, 0.0000001);
    }

    @Test
    void testIsSquare() {
        Random r = new Random(213);
        DoubleStream.generate(() -> Util.nextDouble(r, 1., 5)).limit(5)
                .mapToObj(d -> new Rectangle(d, d))
                .forEach(rect -> assertTrue(rect.isSquare()));
    }

    @Test
    void testReplaceSides() {
        Random r = new Random(213);
        DoubleStream.generate(() -> Util.nextDouble(r, 1., 5)).limit(5)
                .mapToObj(d -> new Rectangle(d, d))
                .collect(Collectors.toMap(k -> k, v -> {
                    v.replaceSides();
                    return v;
                }))
                .forEach((key, value) -> {
                    assertEquals(key.getSideA(), value.getSideB());
                    assertEquals(key.getSideB(), value.getSideA());
                });
    }

    @ParameterizedTest
    @MethodSource("casesHashCodeEquals")
    void testHashCodeEquals(double sideA, double sideB) {
        assertEquals(new Rectangle(sideA), (new Rectangle(sideA)),
                "The methods 'equals' and 'hashCode' must be implemented");
        assertEquals(new Rectangle(sideB).hashCode(), new Rectangle(sideB).hashCode(),
                "The methods 'equals' and 'hashCode' must be implemented");
        assertEquals(new Rectangle(sideA, sideB), (new Rectangle(sideA, sideB)),
                "The methods 'equals' and 'hashCode' must be implemented");
        assertEquals(new Rectangle(sideA, sideB).hashCode(), new Rectangle(sideA, sideB).hashCode(),
                "The methods 'equals' and 'hashCode' must be implemented");
    }
}