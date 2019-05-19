package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
Составить цепочку слов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = reader.readLine();
        reader.close();
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String s;
        String stringFromFile = "";
        while ((s = reader.readLine()) != null) {
            stringFromFile += s;
        }
        reader.close();
        System.out.println(getLine(stringFromFile));
    }

    public static StringBuilder getLine(String... words) {
        StringBuilder sb = new StringBuilder();
        if (words.length == 0) return sb;

        String cities = "";
        for (String w: words) {
            if (cities.length() > 0) cities += " ";
            cities += w;
        }

        String[] arr = cities.split(" ");
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, arr);

        Collections.sort(list);

        String s1 = list.get(0);
        list.remove(0);
        sb.append(s1);

        char bukva = sb.charAt(sb.length()-1);

        label:
        while (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                char bukva2 = list.get(i).toLowerCase().charAt(0);
                if (bukva == bukva2) {
                    sb.append(' ').append(list.get(i));
                    list.remove(i);
                    bukva = sb.charAt(sb.length()-1);
                    continue label;
                }
            }
            sb.append(' ').append(list.get(0));
            bukva = sb.charAt(sb.length()-1);
            list.remove(0);
        }

        return sb;
    }
}
