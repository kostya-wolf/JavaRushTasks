package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.info");

    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));
        if (CurrencyManipulatorFactory.getAllCurrencyManipulators()
                .stream()
                .noneMatch(CurrencyManipulator::hasMoney)) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        } else {
            CurrencyManipulatorFactory.getAllCurrencyManipulators()
                    .forEach(cMan -> System.out.println(cMan.getCurrencyCode() + " - " + cMan.getTotalAmount()));
        }

    }
}
