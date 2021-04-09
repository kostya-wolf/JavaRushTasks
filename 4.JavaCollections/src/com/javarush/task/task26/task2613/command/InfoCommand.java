package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

class InfoCommand implements Command {
    @Override
    public void execute() {
        if (CurrencyManipulatorFactory.getAllCurrencyManipulators()
                .stream()
                .noneMatch(CurrencyManipulator::hasMoney)) {
            ConsoleHelper.writeMessage("No money available.");
        } else {
            CurrencyManipulatorFactory.getAllCurrencyManipulators()
                    .forEach(cMan -> System.out.println(cMan.getCurrencyCode() + " - " + cMan.getTotalAmount()));
        }

    }
}
