package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;

class WithdrawCommand implements Command {
    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator curMan = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        boolean transactionSuccess = true;
        do {
            int withdrawSum = askWithdrawSum(curMan);
            try {
                Map<Integer, Integer> withdrawMap = curMan.withdrawAmount(withdrawSum);
                withdrawMap.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                        .forEach(e -> ConsoleHelper.writeMessage(String.format("\t%d - %d", e.getKey(), e.getValue())));
                ConsoleHelper.writeMessage("Транзакция успешна");
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage("Для желаемой суммы не хватает банкнот");
                transactionSuccess = false;
            }
        } while (!transactionSuccess);
    }

    public static int askWithdrawSum(CurrencyManipulator curMan) throws InterruptOperationException {
        ConsoleHelper.writeMessage("Введите желаемую сумму");
        String sum = ConsoleHelper.readString();
        while (!isWithdrawSumValid(sum, curMan)) {
            ConsoleHelper.writeMessage("Данные некорректны. Введите желаемую сумму");
            sum = ConsoleHelper.readString();
        }
        return Integer.parseInt(sum);
    }

    private static boolean isWithdrawSumValid(String sum, CurrencyManipulator curMan) {
        try {
            int withdrawSum = Integer.parseInt(sum);
            return withdrawSum > 0 && curMan.isAmountAvailable(withdrawSum);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
