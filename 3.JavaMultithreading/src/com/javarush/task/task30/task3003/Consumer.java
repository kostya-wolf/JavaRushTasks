package com.javarush.task.task30.task3003;

import java.util.concurrent.TransferQueue;

/**
 * Created by Волковы on 14.09.2018.
 */
public class Consumer implements Runnable {
    private TransferQueue<ShareItem> queue;

    public Consumer(TransferQueue<ShareItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(450);
        } catch (InterruptedException e) {
        }
        ShareItem item;
        while (true) {
            try {
                item = queue.take();
                System.out.format("Processing %s\n", item.toString());
            } catch (InterruptedException e) {
            }
        }
    }
}
