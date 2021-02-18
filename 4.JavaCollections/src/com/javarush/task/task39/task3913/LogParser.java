package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LogParser implements IPQuery {
    private Path logDir;
    private List<String> logs;
    private List<File> fileList;

    public LogParser(Path logDir) {
        this.logDir = logDir;
    }

    private void logsInit() {
        logs = new ArrayList<>();
        try {
            if (!logDir.toFile().isDirectory()) {
                return;
            }
            fileList = new ArrayList<>();
            getAllLogFiles(logDir.toFile());
            for (File file : fileList) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String log;
                while ((log = reader.readLine()) != null) {
                    logs.add(log);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllLogFiles(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                fileList.add(file);
            } else if (file.isDirectory()) {
                getAllLogFiles(file);
            }
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .map(s -> s[0])
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[1].equals(user))
                .map(s -> s[0])
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        if (event == null) {
            return null;
        }
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[3].split(" ")[0].equals(event.name()))
                .map(s -> s[0])
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        if (status == null) {
            return null;
        }
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[4].equals(status.name()))
                .map(s -> s[0])
                .collect(Collectors.toSet());
    }

    private List<String[]> getLogsAfterBefore(Date after, Date before) {
        if (logs == null) {
            logsInit();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d.M.y H:m:s");
        LocalDateTime afterLCT = after == null ? LocalDateTime.MIN : after.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime beforeLCT = before == null ? LocalDateTime.MAX : before.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return logs.stream()
                .filter(s -> LocalDateTime.parse(s.split("\t")[2], dtf).isAfter(afterLCT)
                        || LocalDateTime.parse(s.split("\t")[2], dtf).isEqual(afterLCT))
                .filter(s -> LocalDateTime.parse(s.split("\t")[2], dtf).isBefore(beforeLCT)
                        || LocalDateTime.parse(s.split("\t")[2], dtf).isEqual(beforeLCT))
                .map(s -> s.split("\t"))
                .collect(Collectors.toList());
    }
}