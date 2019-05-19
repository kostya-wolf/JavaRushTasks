package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    // 0..9 с 48 до 57 --- (char) (Math.random()*10 + 48)
    // A..Z с 65 до 90 --- (char) (Math.random()*26 + 65)
    // a..z с 97 до 122 --- (char) (Math.random()*26 + 97)
    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        Character[] arr = new Character[8];
        arr[(int) (Math.random() * 8)] = 'd';
        boolean small = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                if (small) {
                    result.write((int) (Math.random() * 26 + 97));
                } else {
                    result.write((int) (Math.random() * 26 + 65));
                }
                small = !small;
            } else {
                result.write((int) (Math.random()*10 + 48));
            }
        }
        return result;
    }
}