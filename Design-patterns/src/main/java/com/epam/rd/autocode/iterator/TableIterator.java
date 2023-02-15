package com.epam.rd.autocode.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TableIterator implements Iterator<String> {
    private final String[] table;
    int index;

    public TableIterator(String[] columns, int[] rows){
        table = new String[columns.length * rows.length];

        for(int i = 0; i < columns.length; i++){
            for(int j = 0; j < rows.length; j++){
                int k = i * rows.length + j;
                table[k] = columns[i] + rows[j];
            }
        }
    }

    @Override
    public boolean hasNext() {
        return table.length > index;
    }

    @Override
    public String next() {
        if(hasNext()) {
            return table[index++];
        } else {
            throw new NoSuchElementException();
        }
    }
}
