package com.javarush.task.task22.task2207;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = reader.readLine();
        reader.close();
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String s;
        ArrayList<String> arr = new ArrayList<>();

        while ((s = reader.readLine()) != null) {
            for (String str : s.split(" ")) {
                arr.add(str);
            }
        }
        reader.close();

        ArrayList<String> arrayOfPair = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            String first = arr.get(i);
            arr.remove(i);
            StringBuilder sb = new StringBuilder(first);
            String second = sb.reverse().toString();
            if (arr.contains(second)) {
                Pair pair = new Pair();
                pair.first = first;
                pair.second = second;
                if (!arrayOfPair.contains(pair.toString())) {
                    result.add(pair);
                    arrayOfPair.add(pair.toString());
                }
            }
        }

        for (Pair p: result) {
            System.out.println(p.toString());
        }
    }

    public static class Pair {
        String first;
        String second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                    first == null && second != null ? second :
                    second == null && first != null ? first :
                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
