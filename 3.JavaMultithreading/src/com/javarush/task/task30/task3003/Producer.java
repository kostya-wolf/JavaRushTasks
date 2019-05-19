package com.javarush.task.task30.task3003;

import java.util.concurrent.TransferQueue;

/**
 * Created by Волковы on 14.09.2018.
 */
public class Producer implements Runnable {
    private TransferQueue<ShareItem> queue;

    public Producer(TransferQueue<ShareItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 9; i++) {
            System.out.format("Элемент 'ShareItem-%d' добавлен", i);
            System.out.format("\n");
            queue.offer(new ShareItem("ShareItem-"+i, i));
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
            }
            if (queue.hasWaitingConsumer()) {
                System.out.format("Consumer в ожидании!\n");
            }
        }
    }
}
