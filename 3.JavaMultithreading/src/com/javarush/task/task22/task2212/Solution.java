package com.javarush.task.task22.task2212;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Проверка номера телефона
*/
public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if (telNumber == null || telNumber.equals(""))
            return false;
        boolean result;
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(telNumber);
        int count = 0;
        while (m.find()){
            count++;
        }

        char c1 = telNumber.charAt(0);
        if (c1 == '+'){
            result = count == 12;
        }
        else {
            result = count == 10;
        }
        result = result && telNumber.matches("^(\\d?|\\+?)\\d*(\\(\\d{3}\\))?-?\\d*-?\\d*$");
        return result;
    }

    public static void main(String[] args) {
        String s;

        s = "+380501234567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));
        
        s = "+38(050)1234567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "+38050123-45-67";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "050123-4567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));       
        
        s = "+38)050(1234567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "+38(050)1-23-45-6-7";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "050ххх4567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "050123456";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));        
        
        s = "(0)501234567";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));

        s = "";
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));

        s = null;
        System.out.print(s+"__________");
        System.out.println(checkTelNumber(s));
    }
}
