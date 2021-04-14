package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

public class LoginCommand implements Command {
    private static final String cardNumber = "123456789012";
    private static final String cardPin = "1234";

    @Override
    public void execute() throws InterruptOperationException {
        boolean loginDenied = true;
        do {
            String[] cardNumAndPin = getValidCardNumAndPin();
            if (cardNumber.equals(cardNumAndPin[0]) && cardPin.equals(cardNumAndPin[1])) {
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
