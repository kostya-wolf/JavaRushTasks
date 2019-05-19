package com.javarush.task.task22.task2210;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
StringTokenizer
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getTokens("level22.lesson13.task01", "."));
    }
    public static String [] getTokens(String query, String delimiter) {
        String[] result;
        List<String> arr = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(query, delimiter);
        while (tokenizer.hasMoreTokens()){
            arr.add(tokenizer.nextToken());
        }
        result = arr.toArray(new String[arr.size()]);
        return result;
    }
}
