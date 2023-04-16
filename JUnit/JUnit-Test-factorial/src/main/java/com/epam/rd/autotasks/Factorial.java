package com.epam.rd.autotasks;

import java.math.BigInteger;

public class Factorial {
    public String factorial(String n) {

        if (n == null) {
            throw new IllegalArgumentException();
        }
        if (n.matches("^[0-9]+\\.[0-9]+$")){
            throw new IllegalArgumentException();
        }
        if (Long.parseLong(n) < 0) {
            throw new IllegalArgumentException();
        }

        BigInteger number = new BigInteger(n);
        BigInteger result = new BigInteger("1");

        for(int i = 2; i <= number.longValue(); i++) {
            result = result.multiply(new BigInteger(Integer.toString(i)));
        }
        return result.toString();
    }
}
