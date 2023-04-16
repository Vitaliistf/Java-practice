package com.efimchick.ifmo;

import com.efimchick.ifmo.util.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {
    public int sum(IntStream intStream) {
        return intStream.sum();
    }

    public int production(IntStream intStream) {
        return intStream.reduce(1, (x,y) -> x*y);
    }

    public int oddSum(IntStream intStream) {
        return intStream.filter(i -> i % 2 != 0).sum();
    }

    public Map<Integer, Integer> sumByRemainder(int divider, IntStream intStream) {
        return intStream.boxed()
                .collect(Collectors.toMap(k -> k % divider, v -> v, Integer::sum));
    }

    public Map<Person, Double> totalScores(Stream<CourseResult> results) {
        List<CourseResult> courseResults = results.collect(Collectors.toList());
        double tasksCount = courseResults.stream()
                .flatMap(element -> element.getTaskResults()
                        .keySet()
                        .stream())
                .distinct()
                .count();

        return courseResults.stream()
                .collect(Collectors.toMap(
                        CourseResult::getPerson,
                        v -> v.getTaskResults()
                                .values()
                                .stream()
                                .mapToInt(Integer::intValue)
                                .sum() / tasksCount));
    }

    public double averageTotalScore(Stream<CourseResult> results) {
        List<CourseResult> courseResults = results.collect(Collectors.toList());

        double tasksCount = courseResults.stream()
                .flatMap(element -> element.getTaskResults()
                        .keySet()
                        .stream())
                .distinct()
                .count();

        double studentsCount = courseResults.size();

        return courseResults.stream().flatMap(element -> element.getTaskResults()
                        .values()
                        .stream())
                .mapToInt(Integer::intValue)
                .sum() / (tasksCount * studentsCount);
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> results) {
        List<CourseResult> courseResults = results.collect(Collectors.toList());

        double studentsCount = courseResults.size();

        Map<String, Integer> scoresPerTask = courseResults.stream()
                .flatMap(element -> element.getTaskResults()
                        .entrySet()
                        .stream())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum));

        return scoresPerTask.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue() / studentsCount))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public Map<Person, String> defineMarks(Stream<CourseResult> results) {
        return totalScores(results).entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), Mark.getMark(entry.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public String easiestTask(Stream<CourseResult> results) {
        return averageScoresPerTask(results).entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .orElse(Map.entry("", 0d))
                .getKey();
    }

    public Collector<CourseResult, Table, String> printableStringCollector() {
        return new Collector<>() {
            @Override
            public Supplier<Table> supplier() {
                return Table::new;
            }
            @Override
            public BiConsumer<Table, CourseResult> accumulator() {
                return Table::addCourseResult;
            }
            @Override
            public BinaryOperator<Table> combiner() {
                return null;
            }
            @Override
            public Function<Table, String> finisher() {
                return formatTable -> {
                    StringBuilder strBuilder = new StringBuilder();
                    formatTable.createTable(strBuilder);
                    return strBuilder.toString();
                };
            }
            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
    }

}