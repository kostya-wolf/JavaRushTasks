package com.javarush.task.task25.task2508;

public class TaskManipulator implements Runnable, CustomThreadManipulator {
    private Thread thread;

    @Override
    public void run() {
        System.out.println(thread.getName());
        while (!thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                thread.currentThread().interrupt();
            }
            System.out.println(thread.getName());
        }
    }

    @Override
    public void start(String threadName) {
        this.thread = new Thread(this, threadName);
        thread.start();
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
