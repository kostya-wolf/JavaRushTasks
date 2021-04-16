package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.login");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean loginDenied = true;
        do {
            String[] cardNumAndPin = getValidCardNumAndPin();
            if (validCreditCards.containsKey(cardNumAndPin[0])
                    && validCreditCards.getString(cardNumAndPin[0]).equals(cardNumAndPin[1])) {
                loginDenied = false;
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), cardNumAndPin[0]));
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), cardNumAndPin[0]));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        } while (loginDenied);
    }

    private String[] getValidCardNumAndPin() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("specify.data"));
        String cardNum = ConsoleHelper.readString();
        while (!isCardNumValid(cardNum)) {
            ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            cardNum = ConsoleHelper.readString();
        }
        ConsoleHelper.writeMessage("Введите пин (4 цифры)");
        String cardPin = ConsoleHelper.readString();
        while (!isCardPinValid(cardPin)) {
            ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            cardPin = ConsoleHelper.readString();
        }
        return new String[]{cardNum, cardPin};
    }

    private boolean isCardNumValid(String cardNum) {
        return cardNum.length() == 12;
    }

    private boolean isCardPinValid(String cardPin) {
        return cardPin.length() == 4;
    }
}
