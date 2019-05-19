package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * С помощью класса будем регистрировать события в хранилище
 */
public class StatisticManager {
    private static StatisticManager statisticManager = new StatisticManager();

    public static StatisticManager getInstance() {
        return statisticManager;
    }

    private StatisticStorage statisticStorage = new StatisticStorage();


    private StatisticManager() {
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<String, Long> calculateAdvertisementProfit() {
        List<EventDataRow> selectedVideos = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<String, Long> profitMap = new HashMap<>();
        for (EventDataRow e : selectedVideos) {
            VideoSelectedEventDataRow event = (VideoSelectedEventDataRow) e;
            String videoShownDate = dateFormat.format(event.getDate());
            long amount = event.getAmount();
            Long amountInMap = profitMap.get(videoShownDate);
            amount += amountInMap == null ? 0 : amountInMap;
            profitMap.put(videoShownDate, amount);
        }
        return profitMap;
    }

    public Map<String, Map<String, Integer>> calculateCookWorkloading() {
        List<EventDataRow> cookedOrders = statisticStorage.getStorage().get(EventType.COOKED_ORDER);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Map<String, Map<String, Integer>> workTimeMap = new HashMap<>();

        for (EventDataRow e : cookedOrders) {
            CookedOrderEventDataRow event = (CookedOrderEventDataRow) e;

            String cookingDate = dateFormat.format(event.getDate());
            String cookName = event.getCookName();
            int cookingTimeMin = (int) Math.ceil(event.getTime() / 60d);

            Map<String, Integer> nameAndTimeMap = workTimeMap.get(cookingDate);
            if (nameAndTimeMap == null) {
                nameAndTimeMap = new HashMap<>();
            }
            Integer timeInMap = nameAndTimeMap.get(cookName);
            cookingTimeMin += timeInMap == null ? 0 : timeInMap;
            nameAndTimeMap.put(cookName, cookingTimeMin);
            workTimeMap.put(cookingDate, nameAndTimeMap);
        }
        return workTimeMap;
    }

    /**
     * Хранилище
     */
    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            EventType eventType = data.getType();
            List<EventDataRow> eventDataRowList = storage.get(data.getType());
            eventDataRowList.add(data);
            storage.put(eventType, eventDataRowList);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }
}
