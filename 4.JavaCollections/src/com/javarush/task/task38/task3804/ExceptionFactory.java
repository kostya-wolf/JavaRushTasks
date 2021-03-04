package com.javarush.task.task38.task3804;

public class ExceptionFactory {
    public static Throwable createException(Enum en) {
        String message = null;
        if (en != null) {
            message = en.name().toLowerCase().replace('_', ' ');
            message = message.substring(0, 1).toUpperCase() + message.substring(1);
        }
        if (en instanceof ApplicationExceptionMessage) {
            return new Exception(message);
        } else if (en instanceof DatabaseExceptionMessage) {
            return new RuntimeException(message);
        } else if (en instanceof UserExceptionMessage) {
            return new Error(message);
        } else {
            return new IllegalArgumentException();
        }
    }
}
