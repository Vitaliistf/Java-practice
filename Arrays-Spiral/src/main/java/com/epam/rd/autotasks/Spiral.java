package com.epam.rd.autotasks;

class Spiral {
    static int[][] spiral(int rows, int columns) {
        int[][] result = new int[rows][columns];

        int number = 1;
        for (int i = 0, k = 0; i <= columns / 2; i++, k++) {

            for (int j = k; j < columns-k; j++)
                result[i][j] = number++;

            for (int j = k+1; j < rows-k-1; j++)
                result[j][columns-k-1] = number++;

            for (int j = columns-k-1; number <= rows * columns && j >= k; j--)
                result[rows-i-1][j] = number++;

            for (int j = rows-k-2; j > k; j--)
                result[j][i] = number++;
        }

        return result;
    }
}
