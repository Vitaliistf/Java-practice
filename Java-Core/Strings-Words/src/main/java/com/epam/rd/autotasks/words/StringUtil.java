package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class StringUtil {

    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample){
        int count = 0;

        if(sample == null || sample.isEmpty() || words == null)
            return count;

        sample = sample.strip();
        for(String word : words){
            if(word.strip().equalsIgnoreCase(sample))
                count++;
        }
        return count;
    }

    public static String[] splitWords(String text){
        String regex = "[ ,.;:?!]+";

        if(text == null || text.isEmpty() || text.replaceAll(regex, "").equals(""))
            return null;

        String result = text.replaceAll(regex, " ");
        result = result.strip();
        return result.split(regex);
    }

    public static String convertPath(String path, boolean toWin){
        //I think this solution is may be bad, but I cannot do it better.
        if(!isPathLegal(path))
            return null;

        if(!path.contains("/") && !path.contains("\\") && !path.contains("~"))
            return path;

        String result;
        if(toWin){
            if(path.contains("~") || path.contains("..")) {
                result = path.replaceFirst("~", "C:\\\\User");
            } else {
                result = path.replaceFirst("/", "C:\\\\");
            }
            result = result.replaceAll("/", "\\\\");
        } else {
            result = path.replaceFirst("C:", "");
            result = result.replace("\\User", "~");
            result = result.replaceAll("\\\\", "/");
        }
        return result;
    }

    private static boolean isPathLegal(String path){
        if(path == null || path.equals(""))
            return false;
        else if(path.contains("/") && path.contains("\\"))
            return false;
        else if(path.contains("~") && path.contains("\\"))
            return false;
        else if(path.contains("C:") && path.contains("/"))
            return false;
        else if (path.lastIndexOf("~") != 0 && path.contains("~"))
            return false;
        else if (path.lastIndexOf("C:") != 0 && path.contains("C:"))
            return false;
        else
            return true;
    }

    public static String joinWords(String[] words){
        if(words == null || words.length == 0)
            return null;
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for(String word : words) {
            if(!word.equals(""))
                stringJoiner.add(word);
        }
        return stringJoiner.toString().equals("[]") ? null : stringJoiner.toString();
    }
}