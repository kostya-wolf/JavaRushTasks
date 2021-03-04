package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

import java.time.DayOfWeek;

public class Solution {
    public static Class getFactoryClass() {
        return ExceptionFactory.class;
    }

    public static void main(String[] args) {
        System.out.println(ExceptionFactory.createException(ApplicationExceptionMessage.SOCKET_IS_CLOSED));
        System.out.println(ExceptionFactory.createException(ApplicationExceptionMessage.UNHANDLED_EXCEPTION));
        System.out.println(ExceptionFactory.createException(DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT));
        System.out.println(ExceptionFactory.createException(DatabaseExceptionMessage.NOT_ENOUGH_CONNECTIONS));
        System.out.println(ExceptionFactory.createException(UserExceptionMessage.USER_DOES_NOT_EXIST));
        System.out.println(ExceptionFactory.createException(UserExceptionMessage.USER_DOES_NOT_HAVE_PERMISSIONS));
        System.out.println(ExceptionFactory.createException(DayOfWeek.FRIDAY));
        System.out.println(ExceptionFactory.createException(null));
    }
}