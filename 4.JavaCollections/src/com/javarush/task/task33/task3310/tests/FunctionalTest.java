package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {
    public void testStorage(Shortener shortener) {
        String s1, s2, s3;
        s1 = s3 = Helper.generateRandomString();
        s2 = Helper.generateRandomString();
        Long k1, k2, k3;
        k1 = shortener.getId(s1);
        k2 = shortener.getId(s2);
        k3 = shortener.getId(s3);
        Assert.assertNotEquals(k2, k1);
        Assert.assertNotEquals(k2, k3);
        Assert.assertEquals(k1, k3);
        String v1, v2, v3;
        v1 = shortener.getString(k1);
        v2 = shortener.getString(k2);
        v3 = shortener.getString(k3);
        Assert.assertEquals(s1, v1);
        Assert.assertEquals(s2, v2);
        Assert.assertEquals(s3, v3);
    }

    @Test
    public void testHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }


}