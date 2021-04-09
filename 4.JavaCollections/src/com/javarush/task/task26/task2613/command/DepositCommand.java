package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

class DepositCommand implements Command {
    @Override
    public void execute() {
        String curCode = ConsoleHelper.askCurrencyCode();
        String[] nominalAndCount = ConsoleHelper.getValidTwoDigits(curCode);
        CurrencyManipulator curMan = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        curMan.addAmount(Integer.parseInt(nominalAndCount[0]), Integer.parseInt(nominalAndCount[1]));
    }
}
