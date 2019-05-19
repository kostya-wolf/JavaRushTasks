package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        String result = "incorrect";
        try {
            String s = args[0];
            for (int radix = 2; radix <= 36; radix++) {
                try {
                    BigInteger bi = new BigInteger(s, radix);
                    result = String.valueOf(radix);
                    break;
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {

        }
        System.out.println(result);
    }
}