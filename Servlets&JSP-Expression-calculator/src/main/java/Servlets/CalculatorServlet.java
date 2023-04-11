package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/calc")
public class CalculatorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String expression = request.getParameter("expression").replaceAll(" ", "");
        PrintWriter wr = response.getWriter();
        wr.print(calcLoop(replaceVariables(expression, request), false));
        wr.flush();
        wr.close();
    }

    private static String replaceVariables(String exp, HttpServletRequest req) {
        Matcher matcher = Pattern.compile("[a-z]").matcher(exp);
        while (matcher.find()) {
            exp = matcher.replaceFirst(req.getParameter(matcher.group()));
            matcher = Pattern.compile("[a-z]").matcher(exp);
        }
        return exp;
    }

    private static String calcLoop(String exp, boolean isSimpleExp) {
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
                exp = exp.substring(0, opBr) + calcLoop(exp.substring(opBr + 1, enBr), false) + exp.substring(enBr + 1);
                matcher = Pattern.compile("[\\(\\)]").matcher(exp);
            }

            matcher = Pattern.compile("\\-?\\d+\\s*[\\*\\/]\\s*\\-?\\d+").matcher(exp);
            while (matcher.find()) {
                exp = matcher.replaceFirst(calcLoop(matcher.group(), true));
                matcher = Pattern.compile("\\-?\\d+\\s*[\\*\\/]\\s*\\-?\\d+").matcher(exp);
            }

            matcher = Pattern.compile("\\-?\\d+\\s*[\\+\\-]\\s*\\-?\\d+").matcher(exp);
            while (matcher.find()) {
                exp = matcher.replaceFirst(calcLoop(matcher.group(), true));
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
