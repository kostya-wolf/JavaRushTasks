package com.javarush.task.task40.task4006;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

/* 
Отправка GET-запроса через сокет
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://javarush.ru/social.html"));
    }

    public static void getSite(URL url) {
        try {
            Socket socket = new Socket(url.getHost(), 80);

            PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
            outputStream.println("GET " + url.getPath() + " HTTP/1.1");
            outputStream.println("HOST: " + url.getHost());
            outputStream.println("Connection: close");
            outputStream.println("");
            outputStream.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}