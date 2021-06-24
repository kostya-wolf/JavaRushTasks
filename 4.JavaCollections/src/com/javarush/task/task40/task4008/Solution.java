package com.javarush.task.task40.task4008;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d.M.y");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:m:s");
        try {
            String[] dateTime = date.split(" ");
            if (dateTime.length > 1) {
                LocalDate localDate = LocalDate.parse(dateTime[0], formatterDate);
                LocalTime localTime = LocalTime.parse(dateTime[1], formatterTime);
                printlnDate(localDate);
                printlnTime(localTime);
            } else {
                try {
                    LocalDate localDate = LocalDate.parse(date, formatterDate);
                    printlnDate(localDate);
                } catch (DateTimeParseException ex) {
                    LocalTime localTime = LocalTime.parse(date, formatterTime);
                    printlnTime(localTime);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка парсинга даты");
            e.printStackTrace();
        }
    }

    private static void printlnDate(LocalDate localDate) {
        System.out.println("День: " + localDate.getDayOfMonth());
        System.out.println("День недели: " + localDate.get(ChronoField.DAY_OF_WEEK));
        System.out.println("День месяца: " + localDate.getDayOfMonth());
        System.out.println("День года: " + localDate.getDayOfYear());
        System.out.println("Неделя месяца: " + localDate.get(ChronoField.ALIGNED_WEEK_OF_MONTH));
        System.out.println("Неделя года: " + localDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
        System.out.println("Месяц: " + (localDate.getMonthValue()));
        System.out.println("Год: " + localDate.getYear());
    }

    private static void printlnTime(LocalTime localTime) {
        System.out.println("AM или PM: " + getAmOrPm(localTime.get(ChronoField.AMPM_OF_DAY)));
        System.out.println("Часы: " + localTime.get(ChronoField.CLOCK_HOUR_OF_AMPM));
        System.out.println("Часы дня: " + localTime.getHour());
        System.out.println("Минуты: " + localTime.getMinute());
        System.out.println("Секунды: " + localTime.getSecond());
    }

    private static String getAmOrPm(int i) {
        return i == 0 ? "AM" : "PM";
    }
}
