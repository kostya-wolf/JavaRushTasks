package com.javarush.task.task25.task2512;

/* 
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.interrupt();
        printCause(e);
    }

    private void printCause(Throwable e) {
        if (e.getCause() != null) {
            printCause(e.getCause());
        }
        System.out.println(e.getClass().getName() + ": " + e.getMessage());
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        Thread.setDefaultUncaughtExceptionHandler(sol);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {

        }
        if (true) {
            try {
                System.out.println(2 / 0);
            }
            catch (ArithmeticException ae) {
                throw new RuntimeException("inRuntime", new Exception("GHI", new RuntimeException("DEF")));
            }
        }
    }
}
