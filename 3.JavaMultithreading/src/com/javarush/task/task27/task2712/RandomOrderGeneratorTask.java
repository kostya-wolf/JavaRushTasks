package com.javarush.task.task27.task2712;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Волковы on 07.09.2018.
 */
public class RandomOrderGeneratorTask implements Runnable {
    List<Tablet> tablets = new ArrayList<>();
    int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!tablets.isEmpty()) {
                    Tablet randomTablet = tablets.get((int) (Math.random() * tablets.size()));
                    randomTablet.createTestOrder();
                }
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}