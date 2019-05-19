package com.javarush.task.task22.task2201;

public class ThisUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        final String string = "%s : %s : %s";
        if (Solution.FIRST_THREAD_NAME.equals(t.getName())) {
            System.out.println(getFormattedStringForFirstThread(t, e, string));
        } else if (Solution.SECOND_THREAD_NAME.equals(t.getName())) {
            System.out.println(getFormattedStringForSecondThread(t, e, string));
        } else {
            System.out.println(getFormattedStringForOtherThread(t, e, string));
        }
    }

    protected String getFormattedStringForOtherThread(Thread t, Throwable e, String string) {
        String s1 = e.getClass().getSimpleName();
        Throwable s2 = e.getCause();
        String s3 = t.getName();
        return String.format(string, s1, s2, s3);
    }

    protected String getFormattedStringForSecondThread(Thread t, Throwable e, String string) {
        Throwable s1 = e.getCause();
        String s2 = e.getClass().getSimpleName();
        String s3 = t.getName();
        return String.format(string, s1, s2, s3);
    }

    protected String getFormattedStringForFirstThread(Thread t, Throwable e, String string) {
        String s1 = t.getName();
        String s2 = e.getClass().getSimpleName();
        Throwable s3 = e.getCause();
        return String.format(string, s1, s2, s3);
    }
}

