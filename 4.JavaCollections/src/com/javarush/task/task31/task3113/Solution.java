package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/*
Что внутри папки?
*/
public class Solution extends SimpleFileVisitor<Path> {
    private int folderCount = -1;
    private int fileCount = 0;
    private long totalBytes = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filePath = reader.readLine();
        reader.close();

        Path path = Paths.get(filePath);
        if (!Files.isDirectory(path)) {
            System.out.println(path.toAbsolutePath().toString() + " - не папка");
        } else {
            Solution solution = new Solution();
            Files.walkFileTree(path, solution);
            System.out.println("Всего папок - " + solution.getFolderCount());
            System.out.println("Всего файлов - " + solution.getFileCount());
            System.out.println("Общий размер - " + solution.getTotalBytes());
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        folderCount++;
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        fileCount++;
        totalBytes += attrs.size();
        return super.visitFile(file, attrs);
    }

    public int getFolderCount() {
        return folderCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}
