package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.FileStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.OurHashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new FileStorageStrategy(), 10);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> result = new HashSet<>();
        for (String string : strings) {
            result.add(shortener.getId(string));
        }
        return result;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> result = new HashSet<>();
        for (Long key : keys) {
            result.add(shortener.getString(key));
        }
        return result;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        while (elementsNumber > 0) {
            strings.add(Helper.generateRandomString());
            elementsNumber--;
        }
        Shortener shortener = new Shortener(strategy);

        Date startGetIds = new Date();
        Set<Long> keys = getIds(shortener, strings);
        Date endGetIds = new Date();
        Helper.printMessage(String.valueOf(endGetIds.getTime() - startGetIds.getTime()));

        Date startGetStrings = new Date();
        Set<String> values = getStrings(shortener, keys);
        Date endGetStrings = new Date();
        Helper.printMessage(String.valueOf(endGetStrings.getTime() - startGetStrings.getTime()));

        Helper.printMessage(strings.equals(values) ? "Тест пройден." : "Тест не пройден.");

    }
}
