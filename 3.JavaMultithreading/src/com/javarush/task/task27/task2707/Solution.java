package com.javarush.task.task27.task2707;

import java.util.concurrent.locks.ReentrantLock;

/*
Определяем порядок захвата монитора
*/
public class Solution {
    ReentrantLock lock = new ReentrantLock();

    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    /**
     * Все оказалось просто. Создаем 2 нити. В первой создаем synchronized по о1, потом слип на 500 мс, внутри ставим второй synchronized по о2.
     * Во второй нити просто вызываем someMethodWithSynchronizedBlocks(o1, o2);  запускаем первую нить, запускаем вторую нить и ставим на паузу на 2 секунды.
     * Проверяем состояние второй нити: если НЕ blocked - возвращаем тру, иначе фолс.
     * Логика такова. Мы специально создаем дедлок. Если в даном нам методе синхронизация происходит сначала по 1 объекту, потом по 2,
     * значит дедлока не будет и наш метод просто подождет немного и выполнится,
     * иначе образуется дедлок и вторая нить станет BLOCKED. Надеюсь понятно объяснил)
     */
    public static boolean isNormalLockOrder(final Solution solution, final Object o1, final Object o2) throws Exception {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (o1) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e){
                        System.out.println("InterruptedException = " + e.getMessage());
                    }
                    synchronized (o2) {

                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                solution.someMethodWithSynchronizedBlocks(o1, o2);
            }
        };

        t1.start();
        t2.start();
        Thread.sleep(2000);
        return !t2.isAlive();
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isNormalLockOrder(solution, o1, o2));

    }
}
