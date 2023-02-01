package com.epam.rd.autotasks.arithmeticexpressions;

public class Expressions {

    public static Variable var(String name, int value) {
        return new Variable(name, value);
    }

    public static Expression val(int value) {
        return new Expression() {
            @Override
            public int evaluate() {
                return value;
            }

            @Override
            public String toExpressionString() {
                if(value > 0) {
                    return Integer.toString(value);
                } else {
                    return "(" + value + ")";
                }
            }
        };
    }

    public static Expression sum(Expression... members){
        return new Expression() {
            @Override
            public int evaluate() {
                int result = 0;
                for (Expression member : members) {
                    result += member.evaluate();
                }
                return result;
            }

            @Override
            public String toExpressionString() {
                String result = "(";
                for (int i = 0; i < members.length; i++) {
                    result += members[i].toExpressionString();
                    if(i != members.length-1) {
                        result += " + ";
                    }
                }
                result += ")";
                return result;
            }
        };
    }

    public static Expression product(Expression... members){

        return new Expression() {
            @Override
            public int evaluate() {
                int result = 1;
                for (Expression member : members) {
                    result *= member.evaluate();
                }
                return result;
            }

            @Override
            public String toExpressionString() {
                String result = "(";
                for (int i = 0; i < members.length; i++) {
                    result += members[i].toExpressionString();
                    if(i != members.length-1) {
                        result += " * ";
                    }
                }
                result += ")";
                return result;
            }
        };
    }

    public static Expression difference(Expression minuend, Expression subtrahend){
        return new Expression() {
            @Override
            public int evaluate() {
                return minuend.evaluate() - subtrahend.evaluate();
            }
            @Override
            public String toExpressionString() {
                return "(" + minuend.toExpressionString() + " - " + subtrahend.toExpressionString() + ")";
            }
        };
    }

    public static Expression fraction(Expression dividend, Expression divisor){
        return new Expression() {
            @Override
            public int evaluate() {
                return dividend.evaluate() / divisor.evaluate();
            }

            @Override
            public String toExpressionString() {
                return "(" + dividend.toExpressionString() + " / " + divisor.toExpressionString() + ")";
            }
        };
    }

}
