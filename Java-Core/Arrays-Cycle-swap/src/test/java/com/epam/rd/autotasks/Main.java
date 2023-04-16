package com.epam.rd.autotasks;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        {
//            int[] array = new int[]{1, 2, 3, 4, 5};
//            CycleSwap.cycleSwap(array);
//            System.out.println(Arrays.toString(array));
//        }
//        {
//            int[] array = new int[]{1, 2, 3, 4, 5};
//            CycleSwap.cycleSwap(array, 2);
//            System.out.println(Arrays.toString(array));
//        }
//        {
//            int[] array = new int[]{1, 2, 3, 4, 5};
//            CycleSwap.cycleSwap(array, 5);
//            System.out.println(Arrays.toString(array));
//        }
        {
            int[] array = new int[]{1, 2, 3, 4, 5, 5, 6, 7, 8, 9};
            CycleSwap.cycleSwap(array, 3);
            System.out.println(Arrays.toString(array));
        }
    }
}
