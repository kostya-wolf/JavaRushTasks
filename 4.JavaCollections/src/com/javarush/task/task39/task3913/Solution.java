package com.javarush.task.task39.task3913;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("C:/Temp/"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
        System.out.println(logParser.getUniqueIPs(null, null));
        System.out.println(logParser.getIPsForUser("Amigo", null, null));
        System.out.println(logParser.getIPsForEvent(Event.SOLVE_TASK, null, null));
        System.out.println(logParser.getIPsForStatus(Status.OK, null, null));
    }
}