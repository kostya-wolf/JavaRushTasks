package com.javarush.task.task36.task3605;

import java.io.*;
import java.util.Iterator;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File f = new File(args[0]);
        TreeSet<Character> treeSet = new TreeSet<>();
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(f));
        BufferedReader bufferedReader = new BufferedReader(isReader);
        String s = null;
        while ((s = bufferedReader.readLine()) != null) {
            s = s.toLowerCase();
            for (char c : s.toCharArray()) {
                if (c >= 'a' && c <= 'z') treeSet.add(c);
            }
        }
        bufferedReader.close();
        int index = 0;
        Iterator<Character> it = treeSet.iterator();
        while (it.hasNext() && index < 5) {
            System.out.print(it.next());
            index++;
        }
    }
}
