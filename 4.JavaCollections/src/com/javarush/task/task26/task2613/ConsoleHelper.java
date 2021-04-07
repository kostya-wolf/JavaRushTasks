package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String result = null;
        try {
            result = bis.readLine();
        } catch (IOException e) {
        }
        return result;
    }

    public static String askCurrencyCode() {
        writeMessage("Введите код валюты");
        String curCode = readString();
        while (!isCurrencyValid(curCode)) {
            writeMessage("Данные некорректны. Введите код валюты");
            curCode = readString();
        }
        return curCode.toUpperCase();
    }

    private static boolean isCurrencyValid(String curCode) {
        return curCode.length() == 3;
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        writeMessage("Введите два целых положительных числа - номинал и количество банкнот");
        String nominalAndCount = readString();
        while (!isNominalAndCountValid(nominalAndCount)) {
            writeMessage("Данные некорректны. Введите два целых положительных числа - номинал и количество банкнот");
            nominalAndCount = readString();
        }
        return nominalAndCount.split(" ");
    }

    private static boolean isNominalAndCountValid(String nominalAndCount) {
        try {
            return Arrays.stream(nominalAndCount.split(" "))
                    .map(Integer::parseInt)
                    .filter(d -> d > 0)
                    .count() == 2;
        } catch (Exception e) {
            return false;
        }
    }
}
