package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/* 
Древний Рим
*/

public class Solution {
    private static HashMap<Character, Integer> romanDigital = new HashMap<>();

    static {
        romanDigital.put('I', 1);
        romanDigital.put('V', 5);
        romanDigital.put('X', 10);
        romanDigital.put('L', 50);
        romanDigital.put('C', 100);
        romanDigital.put('D', 500);
        romanDigital.put('M', 1000);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        s = s.toUpperCase();
        if (s.length() == 1) {
            return romanDigital.get(s.charAt(0));
        }
        Integer sum = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int cCurr = romanDigital.get(s.charAt(i));
            int cNext = romanDigital.get(s.charAt(i + 1));
            if (cCurr < cNext) {
                sum -= cCurr;
            } else {
                sum += cCurr;
            }
        }
        sum += romanDigital.get(s.charAt(s.length() - 1));
        return sum;
    }
}
