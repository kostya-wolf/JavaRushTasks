package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.Operation;

import java.util.HashMap;
import java.util.Map;

import static com.javarush.task.task26.task2613.Operation.*;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap;

    static {
        allKnownCommandsMap = new HashMap<>();
        allKnownCommandsMap.put(INFO, new InfoCommand());
        allKnownCommandsMap.put(DEPOSIT, new DepositCommand());
        allKnownCommandsMap.put(WITHDRAW, new WithdrawCommand());
        allKnownCommandsMap.put(EXIT, new ExitCommand());
    }

    private CommandExecutor() {
        throw new UnsupportedOperationException();
    }

    public static final void execute(Operation operation) {
        allKnownCommandsMap.get(operation).execute();
    }
}
