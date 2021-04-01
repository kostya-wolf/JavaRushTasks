package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
        };
        arrayToString(matrix);
        System.out.println("max square = " + maxSquare(matrix));
    }

    public static int maxSquare(int[][] matrix) {
        double result = 0;
        int matLength = matrix.length;
        int matWidth = matrix[0].length;
        int minLength = Math.min(matLength, matWidth);
        for (int i = 0; i < matLength; i++) {
            for (int j = 0; j < matWidth; j++) {
                if (matrix[i][j] == 1) {
                    int squareLen = 1;
                    for (int k = i + 1, l = j + 1; k < minLength && l < minLength; k++, l++) {
                        if (isSquare(i, j, k, l, matrix)) {
                            squareLen++;
                        } else {
                            break;
                        }
                    }
                    result = Math.max(result, Math.pow(squareLen, 2));
                }
            }
        }
        return (int) result;
    }

    private static boolean isSquare(int i, int j, int k, int l, int[][] matrix) {
        for (int i1 = i; i1 <= k; i1++) {
            for (int j1 = j; j1 <= l; j1++) {
                if (matrix[i1][j1] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void arrayToString(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println();
        }
    }
}
