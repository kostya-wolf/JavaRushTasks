package com.javarush.task.task30.task3002;

/* 
Осваиваем методы класса Integer
*/
public class Solution {

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String s) {
        int radix = 10;
        if (s.startsWith("0x")) {
            s = s.substring(2);
            radix = 16;
        } else if (s.startsWith("0b")) {
            s = s.substring(2);
            radix = 2;
        } else if (s.startsWith("0") && s.length() > 1) {
            s = s.substring(1);
            radix = 8;
        }
        int i = Integer.parseInt(s, radix);
        return String.valueOf(i);
    }
}
