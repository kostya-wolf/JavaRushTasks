package com.javarush.task.task39.task3908;

/* 
Возможен ли палиндром?
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строку для палиндрома: ");
        String s = reader.readLine();
        System.out.println("Палиндром " + (isPalindromePermutation(s) ? "возможен" : "невозможен"));
    }

    public static boolean isPalindromePermutation(String s) {
        if (s == null) return false;
        if (s.length() < 2) return true;
        String str = s.replaceAll("\\s", "").toLowerCase();
        Map<Character, Integer> mapa = new HashMap<>();
        for (char c : str.toCharArray()) {
            mapa.putIfAbsent(c, 0);
            mapa.put(c, mapa.get(c) + 1);
        }
        long oddCount = mapa.entrySet().stream()
                .filter(e -> e.getValue() % 2 != 0)
                .count();
        if (str.length() % 2 == 0) {
            return oddCount == 0;
        } else {
            return oddCount == 1;
        }
    }

}
