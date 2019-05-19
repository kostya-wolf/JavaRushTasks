package com.javarush.task.task25.task2506;

import static java.lang.Thread.State.TERMINATED;

/**
 * Created by Волковы on 18.09.2017.
 */
public class LoggingStateThread extends Thread {
    private final Thread target;

    public LoggingStateThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run() {
        String state = target.getState().toString();
        String state2;
        System.out.println(state);
        while (target.getState() != TERMINATED) {
            state2 = target.getState().toString();
            if (!state2.equals(state)) {
                System.out.println(state2);
                state = state2;
            }
        }
        System.out.println(target.getState().toString());
        this.interrupt();
    }
}
