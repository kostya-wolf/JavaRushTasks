package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private static final String cardNumber = "123456789012";
    private static final String cardPin = "1234";
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.verifiedCards");

    @Override
    public void execute() throws InterruptOperationException {
        boolean loginDenied = true;
        do {
            String[] cardNumAndPin = getValidCardNumAndPin();
            if (validCreditCards.containsKey(cardNumAndPin[0])
                    && validCreditCards.getString(cardNumAndPin[0]).equals(cardNumAndPin[1])) {
                ConsoleHelper.writeMessage("Верификация прошла успешно");
                loginDenied = false;
            }
        } while (loginDenied);
    }

    private static String[] getValidCardNumAndPin() throws InterruptOperationException {
        ConsoleHelper.writeMessage("Введите номер карты (12 цифр)");
        String cardNum = ConsoleHelper.readString();
        while (!isCardNumValid(cardNum)) {
            ConsoleHelper.writeMessage("Данные не валидны. Введите номер карты (12 цифр)");
            cardNum = ConsoleHelper.readString();
        }
        ConsoleHelper.writeMessage("Введите пин (4 цифры)");
        String cardPin = ConsoleHelper.readString();
        while (!isCardPinValid(cardPin)) {
            ConsoleHelper.writeMessage("Данные не валидны. Введите пин (4 цифры)");
            cardPin = ConsoleHelper.readString();
        }
        return new String[]{cardNum, cardPin};
    }

    private static boolean isCardNumValid(String cardNum) {
        return cardNum.length() == 12;
    }

    private static boolean isCardPinValid(String cardPin) {
        return cardPin.length() == 4;
    }
}
