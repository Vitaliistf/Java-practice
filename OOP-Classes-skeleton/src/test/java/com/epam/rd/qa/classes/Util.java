package com.epam.rd.qa.classes;

import java.util.Random;

public class Util {
    static double nextDouble(Random r, double origin, double bound) {
        return r.nextDouble() * (bound - origin) + origin;
    }
}
