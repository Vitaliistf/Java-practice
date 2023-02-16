package com.epam.rd.autotasks;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortingTest {

    Sorting sorting = new Sorting();

    @Test(expected = IllegalArgumentException.class)
    public void testNullCase(){
        int[] array = null;
        sorting.sort(array);
        assertNull(array);
    }

    @Test
    public void testEmptyCase(){
        int[] expected = new int[0];
        int[] array = new int[0];
        sorting.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSingleElementArrayCase() {
        int[] expected = new int[]{1};
        int[] array = new int[]{1};
        sorting.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortedArraysCase() {
        int[] expected = new int[]{1,2,3};
        int[] array = new int[]{1,2,3};
        sorting.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testOtherCases() {
        int[] expected = new int[]{1,2,3,6,8};
        int[] array = new int[]{1,6,2,8,3};
        sorting.sort(array);
        assertArrayEquals(expected, array);
    }

}