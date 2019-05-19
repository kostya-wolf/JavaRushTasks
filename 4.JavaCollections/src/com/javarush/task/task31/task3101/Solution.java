package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) {

        File argFile = new File(args[1]);
        File newFile = new File(argFile.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(argFile, newFile);

        try {
            FileOutputStream outputStream = new FileOutputStream(newFile);


            File directory = new File(args[0]);
            List<File> fileList = new ArrayList<>();
            getAllFileNames(directory, fileList);
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });


            for (File f : fileList) {
                FileInputStream inputStream = new FileInputStream(f);

                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                outputStream.write(buffer);
                outputStream.write("\n".getBytes());
                inputStream.close();
            }


            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void getAllFileNames(File directory, List<File> fileList) {
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                if (file.length() <= 50) {
                    fileList.add(file);
                }
            } else if (file.isDirectory()) {
                getAllFileNames(file, fileList);
            }
        }
    }
}
