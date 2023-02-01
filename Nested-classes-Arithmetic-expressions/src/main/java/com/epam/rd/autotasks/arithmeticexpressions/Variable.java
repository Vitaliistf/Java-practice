package com.epam.rd.autotasks.arithmeticexpressions;

public class Variable implements Expression {

    private String name;
    private int value;
    public Variable(String name, int value) {
        if(name != null && !name.equals("")) {
            this.name = name;
            this.value = value;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int evaluate() {
        return value;
    }

    @Override
    public String toExpressionString() {
        return name;
    }
}
