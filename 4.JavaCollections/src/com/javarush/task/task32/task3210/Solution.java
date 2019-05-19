package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(args[0], "rw");
        long pos = Long.valueOf(args[1]);
        String s = args[2];
        raf.seek(pos);
        byte[] b = new byte[s.length()];
        raf.read(b, 0, s.length());

        raf.seek(raf.length());
        if (s.equals(new String(b))) {
            raf.write("true".getBytes());
        } else {
            raf.write("false".getBytes());
        }

        raf.close();
    }
}
