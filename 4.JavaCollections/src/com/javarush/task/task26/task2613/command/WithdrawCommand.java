package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH + "withdraw");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String curCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator curMan = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curCode);
        boolean transactionSuccess;
        do {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            int withdrawSum = askWithdrawSum(curMan);
            try {
                Map<Integer, Integer> withdrawMap = curMan.withdrawAmount(withdrawSum);
                withdrawMap.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                        .forEach(e -> ConsoleHelper.writeMessage(String.format("\t%d - %d", e.getKey(), e.getValue())));
                transactionSuccess = true;
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), withdrawSum, curCode));
            } catch (NotEnoughMoneyException e) {
                transactionSuccess = false;
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        } while (!transactionSuccess);
    }

    public int askWithdrawSum(CurrencyManipulator curMan) throws InterruptOperationException {
        String sum = ConsoleHelper.readString();
        while (!isWithdrawSumValid(sum, curMan)) {
            ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            sum = ConsoleHelper.readString();
        }
        return Integer.parseInt(sum);
    }

    private boolean isWithdrawSumValid(String sum, CurrencyManipulator curMan) {
        try {
            int withdrawSum = Integer.parseInt(sum);
            boolean amountAvailable = curMan.isAmountAvailable(withdrawSum);
            if (!amountAvailable) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            }
            return withdrawSum > 0 && amountAvailable;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
