package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Уникальные подстроки
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int result = 1;
        int i = 0;
        StringBuilder substr = new StringBuilder().append(s.charAt(0));
        while (i < s.length() - 1) {
            if (substr.indexOf(String.valueOf(s.charAt(i + 1))) > -1) {
                if (result < substr.length()) {
                    result = substr.length();
                }
                substr = new StringBuilder().append(s.charAt(i + 1));
            } else {
                substr.append(s.charAt(i + 1));
            }
            i++;
        }
        if (result < substr.length()) {
            result = substr.length();
        }
        return result;
    }
}
