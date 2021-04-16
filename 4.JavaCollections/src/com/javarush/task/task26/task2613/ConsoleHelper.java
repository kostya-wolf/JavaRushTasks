package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.common");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = null;
        try {
            result = bis.readLine();
            if ("EXIT".equals(result.toUpperCase())) {
                writeMessage(res.getString("the.end"));
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
        }
        return result;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String curCode = readString();
        while (!isCurrencyValid(curCode)) {
            writeMessage(res.getString("invalid.data"));
            writeMessage(res.getString("choose.currency.code"));
            curCode = readString();
        }
        return curCode.toUpperCase();
    }

    private static boolean isCurrencyValid(String curCode) {
        return curCode.length() == 3;
    }

    public static String[] getValidTwoDigits(String curCode) throws InterruptOperationException {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), curCode));
        String nominalAndCount = readString();
        while (!isNominalAndCountValid(nominalAndCount)) {
            writeMessage(res.getString("invalid.data"));
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
        writeMessage(res.getString("choose.operation"));
        String opCode = readString();
        while (!isOperaionCodeValid(opCode)) {
            writeMessage(res.getString("invalid.data"));
            opCode = readString();
        }
        return Operation.getAllowableOperationByOrdinal(Integer.parseInt(opCode));
    }

    private static boolean isOperaionCodeValid(String opCode) {
        try {
            Operation operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(opCode));
            writeMessage(res.getString("operation." + operation.name()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
