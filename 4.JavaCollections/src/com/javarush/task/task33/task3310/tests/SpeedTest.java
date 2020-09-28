package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date start = new Date();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        Date end = new Date();
        return end.getTime() - start.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date start = new Date();
        for (Long l : ids) {
            strings.add(shortener.getString(l));
        }
        Date end = new Date();
        return end.getTime() - start.getTime();
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000 ; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();
        long time1 = getTimeToGetIds(shortener1, origStrings, new HashSet<>());
        long time2 = getTimeToGetIds(shortener2, origStrings, new HashSet<>());
        Assert.assertTrue(time1 > time2);
        long time11 = getTimeToGetStrings(shortener1, ids1, new HashSet<>());
        long time22 = getTimeToGetStrings(shortener2, ids2, new HashSet<>());
        Assert.assertEquals(time11, time22, 30);
    }
}
