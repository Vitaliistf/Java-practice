package com.epam.rd.autotasks.springstatefulcalc.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    public static String evaluate(String exp, boolean isSimpleExp) {
        if (isSimpleExp) {
            Matcher matcher = Pattern.compile("\\d[\\*\\/\\+\\-]").matcher(exp);
            matcher.find();
            int lValue = Integer.parseInt(exp.substring(0, matcher.start() + 1));
            int rValue = Integer.parseInt(exp.substring(matcher.start() + 2));
            int result = 0;
            switch (exp.charAt(matcher.start() + 1)) {
                case '+':
                    result = lValue + rValue;
                    break;
                case '-':
                    result = lValue - rValue;
                    break;
                case '*':
                    result = lValue * rValue;
                    break;
                case '/':
                    result = lValue / rValue;
                    break;
            }
            return String.valueOf(result);
        } else {
            Matcher matcher = Pattern.compile("[\\(\\)]").matcher(exp);
            while (matcher.find()) {
                int opBr = matcher.start();
                int enBr = calcBracket(matcher);
                exp = exp.substring(0, opBr) + evaluate(exp.substring(opBr + 1, enBr), false) + exp.substring(enBr + 1);
                matcher = Pattern.compile("[\\(\\)]").matcher(exp);
            }

            matcher = Pattern.compile("\\-?\\d+\\s*[\\*\\/]\\s*\\-?\\d+").matcher(exp);
            while (matcher.find()) {
                exp = matcher.replaceFirst(evaluate(matcher.group(), true));
                matcher = Pattern.compile("\\-?\\d+\\s*[\\*\\/]\\s*\\-?\\d+").matcher(exp);
            }

            matcher = Pattern.compile("\\-?\\d+\\s*[\\+\\-]\\s*\\-?\\d+").matcher(exp);
            while (matcher.find()) {
                exp = matcher.replaceFirst(evaluate(matcher.group(), true));
                matcher = Pattern.compile("\\-?\\d+\\s*[\\+\\-]\\s*\\-?\\d+").matcher(exp);
            }
        }
        return exp;
    }

    private static int calcBracket(Matcher matcher) {
        int bracketsNum = 1;
        while (bracketsNum != 0) {
            matcher.find();
            if (matcher.group().equals("(")) {
                ++bracketsNum;
            } else {
                --bracketsNum;
            }
        }
        return matcher.start();
    }
}
