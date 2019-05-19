package com.javarush.task.task25.task2511;

import java.util.TimerTask;

/* 
Вооружаемся до зубов!
*/
public class Solution extends TimerTask {
    protected TimerTask original;
    protected final Thread.UncaughtExceptionHandler handler;

    public Solution(TimerTask original) {
        if (original == null) {
            throw new NullPointerException();
        }
        this.original = original;
        this.handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                String message = e.getMessage();
                message = message.replaceAll(t.getName(), t.getName().replaceAll(".", "*"));
                System.out.println(message);
            }
        };
    }

    public void run() {
        try {
            original.run();
        } catch (Throwable cause) {
            Thread currentThread = Thread.currentThread();
            handler.uncaughtException(currentThread, new Exception("Blah " + currentThread.getName() + " blah-blah-blah", cause));
        }
    }

    public long scheduledExecutionTime() {
        return original.scheduledExecutionTime();
    }

    public boolean cancel() {
        return original.cancel();
    }

    public static void main(String[] args) {
        Solution sol = new Solution(new TimerTask() {
            @Override
            public void run() {
                if (true) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(2 / 0);
                }
            }
        });
        Thread t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
        t = new Thread(sol);
        t.setUncaughtExceptionHandler(sol.handler);
        t.start();
    }
}