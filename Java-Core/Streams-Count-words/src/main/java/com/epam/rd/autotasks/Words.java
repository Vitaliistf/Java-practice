package com.epam.rd.autotasks;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Words {

    public String countWords(List<String> lines) {
        List<String> allWords = separateWords(lines);

        //Counting words
        Map<String, Long> wordsCount = allWords.stream()
                .filter(word -> word.length() >= 4)
                .collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));

        wordsCount = Words.sortByValueAndKey(wordsCount);
        return Words.mapToString(wordsCount);
    }
    private static String mapToString(Map<String, Long> map){
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 10)
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
    private static List<String> separateWords(List<String> list) {
        String separators = "[-—., :;?!”“’\"‘'/()*]";
        List<String> result = new ArrayList<>();
        list.stream().forEach(string -> result.addAll( List.of(string.toLowerCase().split(separators)) ));
        return result;
    }
    private static Map<String, Long> sortByValueAndKey(Map<String, Long> map) {
        Map<String, Long> result = new LinkedHashMap<>();

        Comparator<Map.Entry<String, Long>> entryComparator = Map.Entry
                .<String, Long> comparingByValue( Comparator.reverseOrder() )
                .thenComparing( Map.Entry.comparingByKey() );

        map.entrySet()
                .stream()
                .sorted(entryComparator)
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));

        return result;
    }
}
