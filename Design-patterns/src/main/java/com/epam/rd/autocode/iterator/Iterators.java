package com.epam.rd.autocode.iterator;

import java.util.Iterator;

class Iterators {

    public static Iterator<Integer> intArrayTwoTimesIterator(int[] array){
        return new TimesIterator(2, array);
    }

    public static Iterator<Integer> intArrayThreeTimesIterator(int[] array) {
        return new TimesIterator(3, array);
    }

    public static Iterator<Integer> intArrayFiveTimesIterator(int[] array) {
        return new TimesIterator(5, array);
    }

    public static Iterable<String> table(String[] columns, int[] rows){
        return () -> new TableIterator(columns, rows);

    }

}
