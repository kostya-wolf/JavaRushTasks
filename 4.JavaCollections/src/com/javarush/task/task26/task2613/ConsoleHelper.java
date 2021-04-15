package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = null;
        try {
            result = bis.readLine();
            if ("EXIT".equals(result.toUpperCase())) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
        }
        return result;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
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

    public static String[] getValidTwoDigits(String wrongData) throws InterruptOperationException {
        writeMessage("Введите два целых положительных числа - номинал и количество банкнот");
        String nominalAndCount = readString();
        while (!isNominalAndCountValid(nominalAndCount)) {
            writeMessage(wrongData);
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

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage("Введите код операции (1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT)");
        String opCode = readString();
        while (!isOperaionCodeValid(opCode)) {
            writeMessage("Данные некорректны. Введите код операции");
            opCode = readString();
        }
        return Operation.getAllowableOperationByOrdinal(Integer.parseInt(opCode));
    }

    private static boolean isOperaionCodeValid(String opCode) {
        try {
            Operation.getAllowableOperationByOrdinal(Integer.parseInt(opCode));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
