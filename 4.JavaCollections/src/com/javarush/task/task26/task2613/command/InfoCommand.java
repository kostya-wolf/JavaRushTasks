package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH + "info");

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
