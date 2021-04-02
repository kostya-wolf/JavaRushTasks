package com.javarush.task.task39.task3905;

import static com.javarush.task.task39.task3905.Color.*;

/* 
Залей меня полностью
*/

public class Solution {
    public static void main(String[] args) {
        Color[][] image = {
                {GREEN, YELLOW, GREEN, YELLOW, GREEN},
                {YELLOW, GREEN, YELLOW, GREEN, YELLOW},
                {GREEN, YELLOW, GREEN, GREEN, GREEN},
                {YELLOW, GREEN, YELLOW, GREEN, YELLOW},
                {GREEN, YELLOW, GREEN, YELLOW, GREEN}
        };
        arrayToString(image);
        PhotoPaint photoPaint = new PhotoPaint();
        System.out.println("закраска: " + photoPaint.paintFill(image, 2, 2, ORANGE));
    }

    public static void arrayToString(Color[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
