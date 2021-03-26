package com.javarush.task.task39.task3903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Неравноценный обмен
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter a number: ");

        long number = Long.parseLong(reader.readLine());
        System.out.println("Please enter the first index: ");
        int i = Integer.parseInt(reader.readLine());
        System.out.println("Please enter the second index: ");
        int j = Integer.parseInt(reader.readLine());

        System.out.println("The result of swapping bits is " + swapBits(number, i, j));
    }

    public static long swapBits(long number, int i, int j) {
        // проверяем, стоит ли флаг в i/j-ой позиции (т.е. ==1)
        boolean i1L = (number & (1L << i)) == (1L << i);
        boolean j1L = (number & (1L << j)) == (1L << j);
        // меняем биты местами, еслиони разные
        if (i1L != j1L) {
            if (i1L) {
                number = number & ~(1L << i); // сброс флага
                number = number | (1L << j);  // установка флага
            } else {
                number = number | (1L << i);  // установка флага
                number = number & ~(1L << j); // сброс флага
            }
        }
        return number;
    }
}
