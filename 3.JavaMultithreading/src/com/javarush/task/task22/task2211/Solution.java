package com.javarush.task.task22.task2211;

import java.io.*;
import java.nio.charset.Charset;

/* 
Смена кодировки
*/
public class Solution {
    static String win1251TestString = "РќР°СЂСѓС€РµРЅРёРµ РєРѕРґРёСЂРѕРІРєРё РєРѕРЅСЃРѕР»Рё?"; //only for your testing

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        String s, s2;
        while ((s = reader.readLine()) != null){
            s2 = new String(s.getBytes(Charset.forName("Windows-1251")), Charset.defaultCharset());
            writer.write(s2);
        }
        reader.close();
        writer.close();
    }
}
