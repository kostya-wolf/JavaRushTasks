package com.javarush.task.task25.task2506;

/* 
Мониторинг состояния нити
*/
public class Solution {
    public static void main(String[] args) throws InterruptedException {
        Thread target = new Thread();
        LoggingStateThread loggingStateThread = new LoggingStateThread(target);

        loggingStateThread.start();

        Thread.sleep(1000);

        target.start();  //NEW
        Thread.sleep(1000); //RUNNABLE
        target.join(1000);
        Thread.sleep(1000);
        target.interrupt(); //TERMINATED
        Thread.sleep(1000);
    }
}
