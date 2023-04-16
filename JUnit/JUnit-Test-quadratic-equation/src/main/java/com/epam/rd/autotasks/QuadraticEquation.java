package com.epam.rd.autotasks;

public class QuadraticEquation {

    public String solve(double a, double b, double c) {
        if(a == 0) {
            throw new IllegalArgumentException();
        }

        double firstRoot, secondRoot;
        double determinant = b * b - 4 * a * c;
        String result;

        if (determinant > 0) {
            firstRoot = (-b + Math.sqrt(determinant)) / (2 * a);
            secondRoot = (-b - Math.sqrt(determinant)) / (2 * a);
            result = firstRoot + " " + secondRoot;

        } else if (determinant == 0) {
            firstRoot = -b / (2 * a);
            result = Double.toString(firstRoot);

        } else {
            result = "no roots";

        }
        return result;
    }

}

