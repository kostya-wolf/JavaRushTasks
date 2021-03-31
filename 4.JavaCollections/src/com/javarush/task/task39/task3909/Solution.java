package com.javarush.task.task39.task3909;

/* 
Одно изменение
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите 1-ю строку: ");
        String s1 = reader.readLine();
        System.out.println("Введите 2-ю строку: ");
        String s2 = reader.readLine();
        System.out.println("\nПолучить из одной строки другую, изменив, добавив или удалив один символ " +
                (isOneEditAway(s1, s2) ? "возможно" : "невозможно"));
    }

    public static boolean isOneEditAway(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        if (first.equals(second)) {
            return true;
        }
        if (first.length() == second.length()) {
            boolean diff = false;
            for (int i = 0; i < first.length(); i++) {
                if (first.charAt(i) != second.charAt(i)) {
                    if (diff) {
                        return false;
                    }
                    diff = true;
                }
            }
            return true;
        } else {
            if (Math.abs(first.length() - second.length()) > 1) {
                return false;
            }
            StringBuilder bigS = first.length() > second.length() ? new StringBuilder(first) : new StringBuilder(second);
            StringBuilder smallS = first.length() < second.length() ? new StringBuilder(first) : new StringBuilder(second);
            for (int i = 0; i < smallS.length(); i++) {
                if (bigS.charAt(i) != smallS.charAt(i)) {
                    smallS.insert(i, bigS.charAt(i));
                    return bigS.toString().equals(smallS.toString());
                }
            }
            smallS.append(bigS.charAt(bigS.length() - 1));
            return bigS.toString().equals(smallS.toString());
        }

    }

}
