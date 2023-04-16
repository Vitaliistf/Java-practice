package com.epam.rd.autotasks;


import java.util.Arrays;

class CycleSwap {
    static void cycleSwap(int[] array) {
        if (array == null || array.length == 0){
            return;
        }
        int[] temp = Arrays.copyOf(array, array.length);
        System.arraycopy(temp, 0, array, 1, array.length-1);
        array[0] = temp[array.length-1];
    }

    static void cycleSwap(int[] array, int shift) {
        if (array == null || array.length == 0){
            return;
        }
        if(shift >= array.length){
            shift %= array.length;
        }
        int[] temp = Arrays.copyOf(array, array.length);
        System.arraycopy(temp, 0, array, shift, array.length-shift);
        System.arraycopy(temp, array.length-shift, array, 0, shift);

    }
}
