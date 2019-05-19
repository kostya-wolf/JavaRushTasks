package com.javarush.task.task32.task3213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (reader != null) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            int i;
            while ((i = bufferedReader.read()) != -1) {
                sb.append((char)(i + key));
            }
            bufferedReader.close();
        }
        return sb.toString();
    }
}
