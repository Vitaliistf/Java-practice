package com.epam.rd.autotasks;

import com.epam.rd.autotasks.Words;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.readAllLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by EE on 2018-09-20.
 */
public class WordsExerciseTest {

    private static final Random rand = new Random();
    private static final Path wapPath = Paths.get("src", "test", "resources", "wap.txt");
    private static final Path wapResultPath = Paths.get("src", "test", "resources", "wap-result.txt");

    @Test
    public void testWarAndPeace() throws IOException {
        final List<String> lines = lines(wapPath, UTF_8).skip(825).collect(Collectors.toList());
        String expected = String.join("\n", readAllLines(wapResultPath, UTF_8));
        assertEquals(expected, new Words().countWords(lines));
    }

    @Test
    public void testRandomCyrillicCases() {
        smallRandomTestCase();
        smallRandomTestCase();
        smallRandomTestCase();
        smallRandomTestCase();
        smallRandomTestCase();
        smallRandomTestCase();
    }


    public void smallRandomTestCase() {
        final List<String> testCase = new ArrayList<>();
        final int[] freqs = rand.ints()
                .map(i -> Math.abs(i) % 10 + 11)
                .distinct()
                .limit(4)
                .sorted()
                .toArray();

        final String[] lines = {"манул красив.", "котенок игрив.", "тигруля в полете.", "Пушкин - наше все"};

        for (int i = 0; i < 4; i++) {
            for (int times = 0; times < freqs[i]; times++) {
                testCase.add(lines[i]);
            }
        }

        Collections.shuffle(testCase);

        String result = String.join("\n",
                "наше - " + freqs[3],
                "пушкин - " + freqs[3],
                "полете - " + freqs[2],
                "тигруля - " + freqs[2],
                "игрив - " + freqs[1],
                "котенок - " + freqs[1],
                "красив - " + freqs[0],
                "манул - " + freqs[0]
        );

        assertEquals(result, new Words().countWords(testCase));
    }

}