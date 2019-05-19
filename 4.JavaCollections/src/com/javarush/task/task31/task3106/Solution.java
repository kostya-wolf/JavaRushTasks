package com.javarush.task.task31.task3106;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        List<String> filePartNames = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            filePartNames.add(args[i]);
        }
        Collections.sort(filePartNames);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (String partName: filePartNames) {
            Files.copy(Paths.get(partName), bos);
        }

        InputStream fis = new ByteArrayInputStream(bos.toByteArray());
        ZipInputStream zis = new ZipInputStream(fis);

        FileOutputStream fos = new FileOutputStream(args[0]);
        byte[] buff = new byte[1024];

        ZipEntry zipEntry;
        while ((zipEntry = zis.getNextEntry()) != null) {
            int len;
            while ((len = zis.read(buff)) > 0) {
                fos.write(buff, 0, len);
            }
        }
        zis.close();

        fos.close();
    }
}
