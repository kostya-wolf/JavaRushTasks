package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private List<String> logs;
    private List<File> fileList;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d.M.y H:m:s");

    public LogParser(Path logDir) {
        this.logDir = logDir;
        logsInit();
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

    private List<String[]> getLogsAfterBeforeWithoutBoundaryDates(Date after, Date before) {
        LocalDateTime afterLCT = after == null ? LocalDateTime.MIN : after.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime beforeLCT = before == null ? LocalDateTime.MAX : before.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return logs.stream()
                .filter(s -> LocalDateTime.parse(s.split("\t")[2], dtf).isAfter(afterLCT))
                .filter(s -> LocalDateTime.parse(s.split("\t")[2], dtf).isBefore(beforeLCT))
                .map(s -> s.split("\t"))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllUsers() {
        return logs.stream()
                .map(s -> s.split("\t")[1])
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .map(s -> s[1])
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[1].equals(user))
                .map(s -> s[3].split(" ")[0])
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[0].equals(ip))
                .map(s -> s[1])
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getDoneEventUsers(after, before, Event.LOGIN.name());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getDoneEventUsers(after, before, Event.DOWNLOAD_PLUGIN.name());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getDoneEventUsers(after, before, Event.WRITE_MESSAGE.name());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getAnyTaskEventUsers(after, before, Event.SOLVE_TASK.name());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getDoneEventUsers(after, before, Event.SOLVE_TASK.name() + " " + task);
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getAnyTaskEventUsers(after, before, Event.DONE_TASK.name());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getDoneEventUsers(after, before, Event.DONE_TASK.name() + " " + task);
    }

    private Set<String> getDoneEventUsers(Date after, Date before, String eventName) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[3].equals(eventName))
                .map(s -> s[1])
                .collect(Collectors.toSet());
    }

    private Set<String> getAnyTaskEventUsers(Date after, Date before, String eventName) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[3].startsWith(eventName))
                .map(s -> s[1])
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        if (event == null) {
            return new HashSet<>();
        }
        return getUserLogs(user, after, before)
                .stream()
                .filter(s -> s[3].split(" ")[0].equals(event.name()))
                .map(s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDatesWhenStatus(after, before, Status.FAILED.name());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesWhenStatus(after, before, Status.ERROR.name());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return getDateWhenFirstTime(user, Event.LOGIN, after, before);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return getUserLogs(user, after, before)
                .stream()
                .filter(s -> s[3].equals(Event.SOLVE_TASK + " " + task))
                .map(s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant()))
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return getUserLogs(user, after, before)
                .stream()
                .filter(s -> s[3].equals(Event.DONE_TASK + " " + task))
                .map(s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant()))
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesForUserAndEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }

    private Set<Date> getDatesWhenStatus(Date after, Date before, String status) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[4].equals(status))
                .map(s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toSet());
    }

    private List<String[]> getUserLogs(String user, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[1].equals(user))
                .collect(Collectors.toList());
    }

    public Date getDateWhenFirstTime(String user, Event event, Date after, Date before) {
        return getDatesForUserAndEvent(user, event, after, before)
                .stream()
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .map(s -> Event.valueOf(s[3].split(" ")[0]))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[0].equals(ip))
                .map(s -> Event.valueOf(s[3].split(" ")[0]))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[1].equals(user))
                .map(s -> Event.valueOf(s[3].split(" ")[0]))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[4].equals(Status.FAILED.name()))
                .map(s -> Event.valueOf(s[3].split(" ")[0]))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[4].equals(Status.ERROR.name()))
                .map(s -> Event.valueOf(s[3].split(" ")[0]))
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return getTaskLogs(Event.SOLVE_TASK, task, after, before).size();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return getTaskLogs(Event.DONE_TASK, task, after, before).size();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Set<Integer> tasks = getTaskForEventSet(Event.SOLVE_TASK, after, before);
        return tasks.stream()
                .collect(Collectors.toMap(
                        t -> t,
                        t -> getNumberOfAttemptToSolveTask(t, after, before)
                ));
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Set<Integer> tasks = getTaskForEventSet(Event.DONE_TASK, after, before);
        return tasks.stream()
                .collect(Collectors.toMap(
                        t -> t,
                        t -> getNumberOfSuccessfulAttemptToSolveTask(t, after, before)
                ));
    }

    private List<String[]> getTaskLogs(Event event, int task, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[3].equals(event + " " + task))
                .collect(Collectors.toList());
    }

    private Set<Integer> getTaskForEventSet(Event event, Date after, Date before) {
        return getTaskForEvenLogs(event, after, before).stream()
                .map(s -> Integer.parseInt(s[3].split(" ")[1]))
                .collect(Collectors.toSet());
    }

    private List<String[]> getTaskForEvenLogs(Event event, Date after, Date before) {
        return getLogsAfterBefore(after, before)
                .stream()
                .filter(s -> s[3].split(" ")[0].equals(event.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Object> execute(String query) {
        String[] queryWords = query.split(" ", 6);
        Set<Object> result = null;
        try {
            if ("get".equals(queryWords[0])) {
                if (queryWords.length == 2) {
                    result = getSetForField1(queryWords[1]);
                }
                if (queryWords.length >= 6) {
                    Pattern pattern = Pattern.compile("\"(.*?)\"");
                    Matcher matcher = pattern.matcher(query);
                    String value = null;
                    Date after = null;
                    Date before = null;
                    if (matcher.find()) {
                        value = matcher.group(1);
                    }
                    if (matcher.find()) {
                        after = getDateFrom(matcher.group(1));
                    }
                    if (matcher.find()) {
                        before = getDateFrom(matcher.group(1));
                    }
                    result = getSetForField2(queryWords[1], queryWords[3], value, after, before);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Set<Object> getSetForField1(String field1) {
        switch (field1) {
            case "ip": {
                return new HashSet<>(getUniqueIPs(null, null));
            }
            case "user": {
                return new HashSet<>(getAllUsers());
            }
            case "date": {
                return getLogsAfterBefore(null, null)
                        .stream()
                        .map(s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant()))
                        .collect(Collectors.toSet());
            }
            case "event": {
                return new HashSet<>(getAllEvents(null, null));
            }
            case "status": {
                return getLogsAfterBefore(null, null)
                        .stream()
                        .map(s -> Status.valueOf(s[4]))
                        .collect(Collectors.toSet());
            }
            default:
                return null;
        }
    }

    private Set<Object> getSetForField2(String field1, String field2, String value, Date after, Date before) {
        Predicate<String[]> predicate = getFilterForField2AndValue(field2, value);
        Function<String[], Object> function = getMapFunctionForField1(field1);
        return getLogsAfterBeforeWithoutBoundaryDates(after, before)
                .stream()
                .filter(predicate)
                .map(function)
                .collect(Collectors.toSet());
    }

    private Predicate<String[]> getFilterForField2AndValue(String field, String value) {
        switch (field) {
            case "ip": {
                return s -> s[0].equals(value);
            }
            case "user": {
                return s -> s[1].equals(value);
            }
            case "date": {
                return s -> LocalDateTime.parse(s[2], dtf).isEqual(LocalDateTime.parse(value, dtf));
            }
            case "event": {
                return s -> s[3].split(" ")[0].equals(value);
            }
            case "status": {
                return s -> s[4].equals(value);
            }
            default:
                return s -> false;
        }
    }

    private Function<String[], Object> getMapFunctionForField1(String field) {
        switch (field) {
            case "ip": {
                return s -> s[0];
            }
            case "user": {
                return s -> s[1];
            }
            case "date": {
                return s -> Date.from(LocalDateTime.parse(s[2], dtf).atZone(ZoneId.systemDefault()).toInstant());
            }
            case "event": {
                return s -> Event.valueOf(s[3].split(" ")[0]);
            }
            case "status": {
                return s -> Status.valueOf(s[4]);
            }
            default:
                return s -> s;
        }
    }

    private Date getDateFrom(String date) {
        return Date.from(LocalDateTime.parse(date, dtf).atZone(ZoneId.systemDefault()).toInstant());
    }
}