package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Планшет директора
 */
public class DirectorTablet {

    public void printAdvertisementProfit() {
        Map<String, Long> profitMap = StatisticManager.getInstance().calculateAdvertisementProfit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<Date> dateList = new ArrayList<>();
        for (String dateString : profitMap.keySet()) {
            try {
                dateList.add(dateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(dateList);
        long total = 0;
        for (Date date : dateList) {
            String dateStr = dateFormat.format(date);
            long am = profitMap.get(dateStr);
            total += am;
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %.2f", dateStr, (am / 100d)));
        }
        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", (total / 100d)));
    }

    public void printCookWorkloading() {
        Map<String, Map<String, Integer>> workTimeMap = StatisticManager.getInstance().calculateCookWorkloading();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<Date> dateList = new ArrayList<>();
        for (String dateString : workTimeMap.keySet()) {
            try {
                dateList.add(dateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(dateList);
        for (Date date : dateList) {
            String dateStr = dateFormat.format(date);
            Map<String, Integer> nameAndTimeMap = workTimeMap.get(dateStr);

            List<String> names = new ArrayList<>();
            for (String name : nameAndTimeMap.keySet()) {
                names.add(name);
            }
            Collections.sort(names);

            ConsoleHelper.writeMessage(dateStr);
            for (String name : names) {
                ConsoleHelper.writeMessage(name + " - " + nameAndTimeMap.get(name) + " min");
            }
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> videoList = StatisticAdvertisementManager.getInstance().getSortedVideoList();
        for (Advertisement adv : videoList) {
            if (adv.getHits() > 0) {
                ConsoleHelper.writeMessage(adv.getName() + " - " + adv.getHits());
            }
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> videoList = StatisticAdvertisementManager.getInstance().getSortedVideoList();
        for (Advertisement adv : videoList) {
            if (adv.getHits() == 0) {
                ConsoleHelper.writeMessage(adv.getName());
            }
        }
    }
}
