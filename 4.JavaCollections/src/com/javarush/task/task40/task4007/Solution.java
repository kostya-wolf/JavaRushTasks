package com.javarush.task.task40.task4007;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* 
Работа с датами
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.M.yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdfDateTime.parse(date));
            printlnDate(calendar);
            printlnTime(calendar);
        } catch (ParseException e) {
            try {
                calendar.setTime(sdfDate.parse(date));
                printlnDate(calendar);
            } catch (ParseException ex) {
                try {
                    calendar.setTime(sdfTime.parse(date));
                    printlnTime(calendar);
                } catch (ParseException exc) {
                    System.out.println("Ошибка парсинга даты");
                    exc.printStackTrace();
                }
            }
        }
    }

    private static void printlnDate(Calendar calendar) {
        System.out.println("День: " + calendar.get(Calendar.DATE));
        System.out.println("День недели: " + getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
        System.out.println("День месяца: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("День года: " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("Неделя месяца: " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Неделя года: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("Месяц: " + (calendar.get(Calendar.MONTH) + 1));
        System.out.println("Год: " + calendar.get(Calendar.YEAR));
    }

    private static int getDayOfWeek(int i) {
        if (i == 1) return 7;
        else return --i;
    }

    private static void printlnTime(Calendar calendar) {
        System.out.println("AM или PM: " + getAmOrPm(calendar.get(Calendar.AM_PM)));
        System.out.println("Часы: " + calendar.get(Calendar.HOUR));
        System.out.println("Часы дня: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("Минуты: " + calendar.get(Calendar.MINUTE));
        System.out.println("Секунды: " + calendar.get(Calendar.SECOND));
    }

    private static String getAmOrPm(int i) {
        return i == 0 ? "AM" : "PM";
    }
}
