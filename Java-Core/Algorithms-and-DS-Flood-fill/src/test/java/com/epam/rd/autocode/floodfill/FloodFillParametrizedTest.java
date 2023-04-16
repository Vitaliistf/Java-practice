package com.epam.rd.autocode.floodfill;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;


public class FloodFillParametrizedTest {

    @ParameterizedTest
    @MethodSource("testCases")
    public void testNaiveFloodFill(String testCaseName, String map, List<String> expectedLog) {
        FloodFill floodFill = FloodFill.getInstance();

        FloodLoggerImpl logger = new FloodLoggerImpl();
        floodFill.flood(map, logger);

        assertEquals(
                String.join("\n\n", expectedLog),
                String.join("\n\n", logger.log),
                () -> testCaseName + " testCase Failed");
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void testNaiveFloodFillWithRecursionCount(String testCaseName, String map, List<String> expectedLog) {
        FloodFill floodFill = FloodFill.getInstance();

        RecursionCountingFloodLoggerImpl logger = new RecursionCountingFloodLoggerImpl();
        floodFill.flood(map, logger);

        assertEquals(
                String.join("\n\n", expectedLog),
                String.join("\n\n", logger.log),
                () -> testCaseName + " testCase Failed"
        );

        assertTrue(
                logger.methodChainCounts.containsAll(
                        LongStream.range(1, expectedLog.size()).boxed().collect(toList())),
                "Looks like implementation is not recursive"
        );
    }

    static Stream<Arguments> testCases() throws IOException {

        final Path testCaseRoot = Paths.get("src", "test", "resources", "test-cases");

        return Files.walk(testCaseRoot, 1)
                .filter(Files::isDirectory)
                .filter(path -> !testCaseRoot.equals(path))
                .map(testCase -> arguments(
                        testCase.getFileName().toString(),
                        readMap(testCase),
                        readLog(testCase)
                ));
    }

    private static String readMap(final Path testCase) {
        try (Stream<String> lines = Files.lines(testCase.resolve("in.txt"))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static List<String> readLog(final Path testCase) {
        try {
            final List<String> allLines = Files.readAllLines(testCase.resolve("out.txt"));

            List<List<String>> linesPerMap = new ArrayList<>();
            linesPerMap.add(new ArrayList<>());

            for (String line : allLines) {
                if (line.isBlank()) {
                    linesPerMap.add(new ArrayList<>());
                } else {
                    linesPerMap.get(linesPerMap.size() - 1).add(line);
                }
            }

            return linesPerMap.stream()
                    .map(mapLines -> String.join("\n", mapLines))
                    .collect(toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
