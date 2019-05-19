package com.javarush.task.task28.task2805;

/**
 * Created by Волковы on 20.07.2018.
 */
public class MyThread extends Thread {
    private static int priora = Thread.MIN_PRIORITY;

    public MyThread() {
        super();
        setPriority(getPriora());
    }

    public MyThread(Runnable target) {
        super(target);
        setPriority(getPriora());
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        setPriority(getPriora(group.getMaxPriority()));
    }

    public MyThread(String name) {
        super(name);
        setPriority(getPriora());
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
        setPriority(getPriora());
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        setPriority(getPriora(group.getMaxPriority()));
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        setPriority(getPriora(group.getMaxPriority()));
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        setPriority(getPriora(group.getMaxPriority()));
    }

    private int getPriora() {
        if (priora > Thread.MAX_PRIORITY) {
            priora = Thread.MIN_PRIORITY;
        }
        return priora++;
    }

    private int getPriora(int groupMaxPriority) {
        int result = getPriora();
        return result > groupMaxPriority ? groupMaxPriority : result;
    }

}
