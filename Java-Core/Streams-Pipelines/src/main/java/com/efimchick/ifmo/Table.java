package com.efimchick.ifmo;

import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {
    private static final String STUDENT = "Student";
    private static final String TOTAL = "Total";
    private static final String MARK = "Mark";
    private static final String AVERAGE = "Average";
    private static final String SEPARATOR = "| ";
    private static final String LAST_SEPARATOR = "|\n";
    private static final String VERY_LAST_SEPARATOR = "|";
    private static final int MIN_NUMBER_OF_COLUMNS = 3;
    private static final int MIN_FIRST_COLUMN_WIDTH = STUDENT.length() + 1;
    private static final int TOTAL_COLUMN_WIDTH = TOTAL.length() + 1;
    private static final int MARK_COLUMN_WIDTH = MARK.length() + 1;
    private static final int MIN_WIDTH_OF_TASK_COLUMN = 6;
    private static final String LEFT_ALIGNMENT = "%-";
    private static final String RIGHT_ALIGNMENT = "%";
    private static final String PLACEHOLDER_FOR_DIGIT = "d";
    private static final String PLACEHOLDER_FOR_STRING = "s";
    private static final String PLACEHOLDER_FOR_DOUBLE_TWO_DECIMAL_PLACES = ".2f";
    private static final String WHITESPACE = " ";
    private final List<CourseResult> COURSE_RES = new ArrayList<>();
    private List<String> tasks = new ArrayList<>();
    private List<String> students = new ArrayList<>();
    private String headerFormatPattern;
    private String studentFormatPattern;
    private String footerFormatPattern;
    private int numberOfColumns;

    public void addCourseResult(final CourseResult courseResult) {
        COURSE_RES.add(courseResult);
    }

    public void createTable(final StringBuilder builder) {
        initializeTable();
        fillTable(builder);
    }

    private void initializeTable() {
        setTasks();
        setStudents();
        defineNumberOfColumns();
        createFormatPattens();
    }

    private void createFormatPattens() {
        createHeaderFormatPattern();
        createStudentFormatPattern();
        createFooterFormatPattern();
    }

    private void fillTable(final StringBuilder builder) {
        addFirstRow(builder);
        addStudentsRows(builder);
        addLastRow(builder);
    }

    private void defineNumberOfColumns() {
        numberOfColumns = MIN_NUMBER_OF_COLUMNS + tasks.size();
    }

    private void addFirstRow(final StringBuilder builder) {
        Object[] formatArgs = new Object[numberOfColumns];
        int index = 0;
        formatArgs[index++] = STUDENT;
        for (String task : tasks) {
            formatArgs[index++] = task;
        }
        formatArgs[index++] = TOTAL;
        formatArgs[index] = MARK;
        builder.append(String.format(headerFormatPattern, formatArgs));
    }

    private void addStudentsRows(final StringBuilder builder) {
        Map<Person, Double> totalScores = new Collecting().totalScores(COURSE_RES.stream());
        Map<Person, String> marks = new Collecting().defineMarks(COURSE_RES.stream());
        for (final String student : students) {
            Object[] formatArgs = new Object[numberOfColumns];
            int index = 0;
            formatArgs[index++] = student;
            for (final String task : tasks) {
                formatArgs[index++] = getStudentScoreForTask(student, task);
            }
            formatArgs[index++] = getStudentTotalScore(totalScores, student);
            formatArgs[index] = getStudentMark(marks, student);
            builder.append(String.format(studentFormatPattern, formatArgs));
        }
    }

    private void addLastRow(final StringBuilder builder) {
        Collecting collecting = new Collecting();
        Map<String, Double> taskScores = collecting.averageScoresPerTask(COURSE_RES.stream());
        Object[] formatArgs = new Object[numberOfColumns];
        int index = 0;
        formatArgs[index++] = AVERAGE;
        for (String task : tasks) {
            formatArgs[index++] = taskScores.get(task);
        }
        double totalResult = collecting.averageTotalScore(COURSE_RES.stream());
        formatArgs[index++] = totalResult;
        formatArgs[index] = Mark.getMark(totalResult);
        builder.append(String.format(footerFormatPattern, formatArgs));
    }

    private int getStudentScoreForTask(final String studentName, final String task) {
        return COURSE_RES.stream()
                .filter(cr -> (cr.getPerson().getLastName() + WHITESPACE
                        + cr.getPerson().getFirstName()).equals(studentName))
                .flatMap(cr -> cr.getTaskResults().entrySet().stream())
                .filter(e -> e.getKey().equals(task))
                .mapToInt(Map.Entry::getValue)
                .findFirst().orElse(0);
    }

    private String getStudentMark(final Map<Person, String> marks, final String studentName) {
        return marks.entrySet().stream()
                .filter(e -> (e.getKey().getLastName() + WHITESPACE
                        + e.getKey().getFirstName()).equals(studentName))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());
    }

    private double getStudentTotalScore(final Map<Person, Double> totalScores,
                                        final String studentName) {
        return totalScores.entrySet().stream()
                .filter(e -> (e.getKey().getLastName() + WHITESPACE
                        + e.getKey().getFirstName()).equals(studentName))
                .mapToDouble(Map.Entry::getValue).findFirst().orElse(0.0);
    }

    private int defineFirstColumnWidth() {
        return Math.max(MIN_FIRST_COLUMN_WIDTH, 1 + COURSE_RES.stream()
                .map(CourseResult::getPerson)
                .mapToInt(p -> (p.getFirstName() + WHITESPACE + p.getLastName()).length())
                .max().orElse(0));
    }

    private int defineTaskColumnWidth(final int numberOfTask) {
        return Math.max(MIN_WIDTH_OF_TASK_COLUMN, tasks.get(numberOfTask).length() + 1);
    }

    private void createHeaderFormatPattern() {
        StringBuilder formatPattern = new StringBuilder()
                .append(LEFT_ALIGNMENT)
                .append(defineFirstColumnWidth())
                .append(PLACEHOLDER_FOR_STRING)
                .append(SEPARATOR);
        for (int i = 0; i < tasks.size(); i++) {
            formatPattern.append(LEFT_ALIGNMENT)
                    .append(defineTaskColumnWidth(i))
                    .append(PLACEHOLDER_FOR_STRING)
                    .append(SEPARATOR);
        }
        formatPattern.append(LEFT_ALIGNMENT)
                .append(TOTAL_COLUMN_WIDTH)
                .append(PLACEHOLDER_FOR_STRING)
                .append(SEPARATOR)
                .append(LEFT_ALIGNMENT)
                .append(MARK_COLUMN_WIDTH)
                .append(PLACEHOLDER_FOR_STRING)
                .append(LAST_SEPARATOR);
        this.headerFormatPattern = formatPattern.toString();
    }

    private void createStudentFormatPattern() {
        StringBuilder formatPattern = new StringBuilder()
                .append(LEFT_ALIGNMENT)
                .append(defineFirstColumnWidth())
                .append(PLACEHOLDER_FOR_STRING)
                .append(SEPARATOR);
        for (int i = 0; i < tasks.size(); i++) {
            formatPattern
                    .append(RIGHT_ALIGNMENT)
                    .append(defineTaskColumnWidth(i) - 1)
                    .append(PLACEHOLDER_FOR_DIGIT)
                    .append(WHITESPACE)
                    .append(SEPARATOR);
        }
        formatPattern.append(RIGHT_ALIGNMENT)
                .append(TOTAL_COLUMN_WIDTH - 1)
                .append(PLACEHOLDER_FOR_DOUBLE_TWO_DECIMAL_PLACES)
                .append(WHITESPACE)
                .append(SEPARATOR)
                .append(RIGHT_ALIGNMENT)
                .append(MARK_COLUMN_WIDTH - 1)
                .append(PLACEHOLDER_FOR_STRING)
                .append(WHITESPACE)
                .append(LAST_SEPARATOR);
        this.studentFormatPattern = formatPattern.toString();
    }

    private void createFooterFormatPattern() {
        StringBuilder formatPattern = new StringBuilder()
                .append(LEFT_ALIGNMENT)
                .append(defineFirstColumnWidth())
                .append(PLACEHOLDER_FOR_STRING)
                .append(SEPARATOR);
        for (int i = 0; i < tasks.size(); i++) {
            formatPattern
                    .append(RIGHT_ALIGNMENT)
                    .append(defineTaskColumnWidth(i) - 1)
                    .append(PLACEHOLDER_FOR_DOUBLE_TWO_DECIMAL_PLACES)
                    .append(WHITESPACE)
                    .append(SEPARATOR);
        }
        formatPattern.append(RIGHT_ALIGNMENT)
                .append(TOTAL_COLUMN_WIDTH - 1)
                .append(PLACEHOLDER_FOR_DOUBLE_TWO_DECIMAL_PLACES)
                .append(WHITESPACE)
                .append(SEPARATOR)
                .append(RIGHT_ALIGNMENT)
                .append(MARK_COLUMN_WIDTH - 1)
                .append(PLACEHOLDER_FOR_STRING)
                .append(WHITESPACE)
                .append(VERY_LAST_SEPARATOR);
        this.footerFormatPattern = formatPattern.toString();
    }

    private void setTasks() {
        tasks = COURSE_RES.stream()
                .flatMap(r -> r.getTaskResults().keySet().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private void setStudents() {
        students = COURSE_RES.stream()
                .map(CourseResult::getPerson)
                .sorted(Comparator.comparing(Person::getLastName))
                .map(p -> p.getLastName() + WHITESPACE + p.getFirstName())
                .collect(Collectors.toList());
    }
}