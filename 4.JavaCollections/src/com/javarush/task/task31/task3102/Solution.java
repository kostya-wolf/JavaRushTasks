package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> result = new ArrayList<>();
        Queue<File> queue = new PriorityQueue<>();
        queue.add(new File(root));

        while (!queue.isEmpty()) {
            File directory = queue.poll();
            for (File f : directory.listFiles()) {
                if (f.isFile()) {
                    result.add(f.getAbsolutePath());
                } else if (f.isDirectory()) {
                    queue.add(f);
                }
            }
        }

        return result;

    }

    public static void main(String[] args) throws IOException {
        List<String> res = getFileTree("C:\\TMP\\javaTemp");
        for (String s : res) {
            System.out.println(s);
        }
    }
}
