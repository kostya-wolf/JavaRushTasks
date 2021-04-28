package com.javarush.task.task40.task4011;

import java.net.MalformedURLException;
import java.net.URL;

/* 
Свойства URL
*/

public class Solution {
    public static void main(String[] args) {
        decodeURLString("https://www.amrood.com/index.htm?language=en#j2se");
    }

    public static void decodeURLString(String s) {
        try {
            URL url = new URL(s);
            System.out.println(url.getProtocol());
            System.out.println(url.getAuthority());
            System.out.println(url.getFile());
            System.out.println(url.getHost());
            System.out.println(url.getPath());
            System.out.println(url.getPort());
            System.out.println(url.getDefaultPort());
            System.out.println(url.getQuery());
            System.out.println(url.getRef());
        } catch (MalformedURLException e) {
            System.out.println(String.format("Parameter %s is not a valid URL.", s));
        }
    }
}

