package com.javarush.task.task32.task3201;

import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(args[0], "rw");
        long length = raf.length();
        long pos = Long.valueOf(args[1]);
        String s = args[2];
        if (length < pos) {
            pos = length;
        }
        raf.seek(pos);
        raf.write(s.getBytes());
        raf.close();
    }
}
