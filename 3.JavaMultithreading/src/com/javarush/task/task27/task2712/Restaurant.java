package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Волковы on 25.08.2018.
 */
public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Cook cook = new Cook("Amigo");
        cook.setQueue(orderQueue);
        Cook cook2 = new Cook("Cesar");
        cook2.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook2.addObserver(waiter);

        List<Tablet> tablets = new ArrayList<>();
        for (int i = 50; i < 55; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread randomOrderThread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        randomOrderThread.start();

        Thread cookAmigoThread = new Thread(cook);
        cookAmigoThread.setDaemon(true);
        cookAmigoThread.start();

        Thread cookCesarThreaf = new Thread(cook2);
        cookCesarThreaf.setDaemon(true);
        cookCesarThreaf.start();

        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
        }
        randomOrderThread.interrupt();
    }
}
