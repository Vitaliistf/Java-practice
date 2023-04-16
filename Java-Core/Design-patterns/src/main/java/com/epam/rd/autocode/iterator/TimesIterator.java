package com.epam.rd.autocode.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TimesIterator implements Iterator<Integer> {
    private int index;
    private final int times;
    private final int[] array;

    public TimesIterator(int times, int[] array) {
        this.times = times;
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return array.length > index / times;
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            return array[index++ / times];
        } else {
            throw new NoSuchElementException();
        }
    }
}
