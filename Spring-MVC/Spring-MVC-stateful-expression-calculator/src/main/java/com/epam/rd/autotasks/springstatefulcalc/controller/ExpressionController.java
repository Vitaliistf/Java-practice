package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.service.Evaluator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/calc")
public class ExpressionController {

    @PutMapping("/expression")
    public ResponseEntity<String> putExpression(HttpSession session, @RequestBody String expression) {
        if (expression == null || expression.length() == 0 ||
                expression.replaceAll("[+*/-]","").length()==expression.length()) {
            return ResponseEntity.status(400).body("Incorrect expression");
        } else {
            int statusCode = session.getAttribute("expression") == null ? 201 : 200;
            session.setAttribute("expression", expression);
            return ResponseEntity.status(statusCode).body("OK");
        }
    }

    @PutMapping("/{varName}")
    public ResponseEntity<String> putVariable(HttpSession session, @RequestBody String value, @PathVariable String varName) {
        if (value.matches("-?[0-9]+")) {
            int num = Integer.parseInt(value);
            if (num > 10000 || num < -10000) {
                return ResponseEntity.status(403).body("Values should be in [-10000,10000] range");
            } else {
                int statusCode = session.getAttribute(varName) == null ? 201 : 200;
                session.setAttribute(varName, value);
                return ResponseEntity.status(statusCode).build();
            }
        } else if (session.getAttribute(value) != null) {
            int statusCode = session.getAttribute(varName) == null ? 201 : 200;
            session.setAttribute(varName, value);
            return ResponseEntity.status(statusCode).build();
        } else {
            return ResponseEntity.status(400).body("Incorrect value/reference for variable");
        }
    }

    @GetMapping("/result")
    public ResponseEntity<String> getResult(HttpSession session) {
        StringBuilder exp = new StringBuilder(((String) session.getAttribute("expression"))
                .replaceAll(" ", ""));
        for(int i = 0; i < exp.length(); i++) {
            while(Character.isAlphabetic(exp.charAt(i))) {
                String value = (String) session.getAttribute(String.valueOf(exp.charAt(i)));
                if (value == null) {
                    return ResponseEntity.status(409).body("Variable " + exp.charAt(i) + " has no value");
                }
                exp.replace(i, i + 1, value);
            }
        }
       return ResponseEntity.status(200).body(Evaluator.evaluate(exp.toString(), false));
    }

    @DeleteMapping("/{varName}")
    public ResponseEntity<String> delVariable(HttpSession session, @PathVariable String varName) {
        session.removeAttribute(varName);
        return ResponseEntity.status(204).body("Variable " + varName + " has been deleted.");
    }

}
