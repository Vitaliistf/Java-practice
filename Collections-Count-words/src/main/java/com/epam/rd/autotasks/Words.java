package com.epam.rd.autotasks;


import java.util.*;

public class Words {
    public String countWords(List<String> lines) {
        List<String> allWords = separateWords(lines);
        HashMap<String, Integer> wordsCount = new HashMap<>();
        //Counting all words and save this info in hashmap
        for (String str : allWords) {
            if(str.length() >= 4) {
                if (wordsCount.containsKey(str)) {
                    wordsCount.put(str, wordsCount.get(str) + 1);
                } else {
                    wordsCount.put(str, 1);
                }
            }
        }
        wordsCount = sortByValueAndKey(wordsCount);
        return mapToString(wordsCount);
    }
    private static String mapToString(HashMap<String, Integer> map){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue() >= 10)
                result.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }
    private static List<String> separateWords(List<String> list) {
        String separators = "[-—., :;?!”“’\"‘'/()*]";
        List<String> result = new ArrayList<>();
        for (String str : list) {
            result.addAll(List.of(str.toLowerCase().split(separators)));
        }
        return result;
    }
    private static HashMap<String, Integer> sortByValueAndKey(HashMap<String, Integer> hashmap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hashmap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> object1, Map.Entry<String, Integer> object2) {
                int valuesCompared = object1.getValue().compareTo(object2.getValue());
                if (valuesCompared == 0) {
                    return object1.getKey().compareTo(object2.getKey());
                } else {
                    return valuesCompared * -1;
                }
            }
        });
        HashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
