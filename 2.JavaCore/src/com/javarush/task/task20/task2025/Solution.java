package com.javarush.task.task20.task2025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Алгоритмы-числа
*/
public class Solution {
    public static long[] getNumbers(long N) {
        long[] result = null;

        int length = calcLengthOfLong(N);
        List<Long> list = generate(length-1);

        int newSize = list.size();
        for (long d: list) {
            if (d > N ) newSize--;
        }

        result = new long[newSize];

        for (int i=0; i<newSize; i++){
            result[i] = list.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("Long.MAX_VALUE= "+Long.MAX_VALUE);
        System.out.println("Integer.MAX_VALUE= "+Integer.MAX_VALUE);
        long N = Long.MAX_VALUE/1;
        System.out.println("N = "+N);
        long startTime = System.currentTimeMillis();
        long startTime2 = System.nanoTime();
        long[] res = getNumbers(N);
        System.out.println(((System.currentTimeMillis() - startTime) / 1000)+" секунд");
        System.out.println((System.nanoTime() - startTime2)+" наносекунд");
        for (int k = 0; k < res.length; k++)
        {
            System.out.print(res[k] + "_");
        }
    }

    public static int calcLengthOfLong(long N)
    {
        if (N > 999999)
        {
            if (N < 10000000L) return 7;
            else if (N < 100000000L) return 8;
            else if (N < 1000000000L) return 9;
            else if (N < 10000000000L) return 10;
            else if (N < 100000000000L) return 11;
            else if (N < 1000000000000L) return 12;
            else if (N < 10000000000000L) return 13;
            else if (N < 100000000000000L) return 14;
            else if (N < 1000000000000000L) return 15;
            else if (N < 10000000000000000L) return 16;
            else if (N < 100000000000000000L) return 17;
            else if (N < 1000000000000000000L) return 18;
            else return 19;
        } else
        {
            if (N > 999)
            {
                if (N < 10000) return 4;
                else if (N < 100000) return 5;
                else return 6;
            } else
            {
                if (N > 99) return 3;
                else if (N > 9) return 2;
                else return 1;
            }
        }
    }

    private static int N;
    private static int[] digitsMultiSet;
    private static int[] testMultiSet;

    private static List<Long> results;
    private static long maxPow;
    private static long minPow;

    private static long[][] pows;

    private static void genPows(int N) {
        if (N > 20) throw new IllegalArgumentException();
        pows = new long[10][N + 1];
        for (int i = 0; i < pows.length; i++) {
            long p = 1;
            for (int j = 0; j < pows[i].length; j++) {
                pows[i][j] = p;
                p *= i;
            }
        }
    }

    private static boolean check(long pow) {
        if (pow >= maxPow) return false;
        if (pow < minPow) return false;

        for (int i = 0; i < 10; i++) {
            testMultiSet[i] = 0;
        }

        while (pow > 0) {
            int i = (int) (pow % 10);
            testMultiSet[i]++;
            if (testMultiSet[i] > digitsMultiSet[i]) return false;
            pow = pow / 10;
        }

        for (int i = 0; i < 10; i++) {
            if (testMultiSet[i] != digitsMultiSet[i]) return false;
        }

        return true;
    }

    private static void search(int digit, int unused, long pow) {
        if (pow >= maxPow) return;

        if (digit == -1) {
            if (check(pow)) results.add(pow);
            return;
        }

        if (digit == 0) {
            digitsMultiSet[digit] = unused;
            search(digit - 1, 0, pow + unused * pows[digit][N]);
        } else {
            if (pow + unused * pows[digit][N] < minPow) return;

            long p = pow;
            for (int i = 0; i <= unused; i++) {
                digitsMultiSet[digit] = i;
                search(digit - 1, unused - i, p);
                if (i != unused) {
                    p += pows[digit][N];
                }
            }
        }
    }

    public static List<Long> generate(int maxN) {
        if (maxN >= 20) throw new IllegalArgumentException();

        genPows(maxN);
        results = new ArrayList<>();
        digitsMultiSet = new int[10];
        testMultiSet = new int[10];

        for (N = 1; N <= maxN; N++) {
            minPow = (long) Math.pow(10, N - 1);
            maxPow = (long) Math.pow(10, N);

            search(9, N, 0);
        }

        Collections.sort(results);

        return results;
    }
}
