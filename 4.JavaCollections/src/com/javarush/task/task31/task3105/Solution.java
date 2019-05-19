package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        FileInputStream zipFile = new FileInputStream(args[1]);
        ZipInputStream zipIn = new ZipInputStream(zipFile);

        Map<String, String> zipEntryHashMap = new LinkedHashMap<>();
        byte[] buffer = new byte[1024];

        ZipEntry zipEntry;
        while ((zipEntry = zipIn.getNextEntry()) != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            while ((len = zipIn.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            zipEntryHashMap.put(zipEntry.getName(), bos.toString());
        }
        zipIn.closeEntry();
        zipIn.close();

        File f = new File(args[0]);

        FileOutputStream resultZipFile = new FileOutputStream(args[1]);
        ZipOutputStream zipOut = new ZipOutputStream(resultZipFile);

        String newZipEntryName = "new/" + f.getName();
        zipOut.putNextEntry(new ZipEntry(newZipEntryName));
        Files.copy(f.toPath(), zipOut);

        for (String zipEnName : zipEntryHashMap.keySet()) {
            if (!zipEnName.equals(newZipEntryName)) {
                zipOut.putNextEntry(new ZipEntry(zipEnName));
                zipOut.write(zipEntryHashMap.get(zipEnName).getBytes());
            }
        }

        zipOut.close();
    }
}
