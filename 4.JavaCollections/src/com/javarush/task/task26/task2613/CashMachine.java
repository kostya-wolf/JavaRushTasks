package com.javarush.task.task26.task2613;

import java.util.Locale;

public class CashMachine {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        String curCode = ConsoleHelper.askCurrencyCode();
        String[] nominalAndCount = ConsoleHelper.getValidTwoDigits(curCode);
        CurrencyManipulator curMan = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        curMan.addAmount(Integer.parseInt(nominalAndCount[0]), Integer.parseInt(nominalAndCount[1]));
    }
}
