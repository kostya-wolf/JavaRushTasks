package com.javarush.task.task39.task3904;

/*
Лестница
*/

public class Solution {
    private static int n = 70;

    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        if (n < 0) return 0;
        if (n == 0 || n == 1) return 1;
        long result = 0;
        int i = 0;
        long t1 = 0;
        long t2 = 0;
        long t3 = 1;
        while (i++ < n) {
            result = t1 + t2 + t3;
            t1 = t2;
            t2 = t3;
            t3 = result;
        }
        return result;
    }
}
