package com.javarush.task.task39.task3910;

/* 
isPowerOfThree
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите число: ");
        int n = Integer.parseInt(reader.readLine());
        System.out.println("это " + (isPowerOfThree(n) ? "" : "не ") + "целочисленная степень 3-ки");
    }

    public static boolean isPowerOfThree(int n) {
        int pow3 = 1;
        int i = 1;
        while (n >= pow3 && i < 21) {
            if (n == pow3) {
                return true;
            }
            pow3 = (int) Math.pow(3, i);
            i++;
        }
        return false;
    }
}

