package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Волковы on 03.12.2017.
 */
public class Producer implements Runnable {
    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    public void run() {
        Thread currentThread = Thread.currentThread();
        int i = 1;
        try {
            while (true) {
                map.put(String.valueOf(i), "Some text for " + i++);
                currentThread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(currentThread.getName() + " thread was terminated");
        }
    }
}
