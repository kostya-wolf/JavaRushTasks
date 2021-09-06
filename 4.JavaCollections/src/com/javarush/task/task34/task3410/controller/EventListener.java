package com.javarush.task.task34.task3410.controller;

import com.javarush.task.task34.task3410.model.Direction;

public interface EventListener {
    // передвинуть объект в определенном направлении
    void move(Direction direction);

    //начать заново текущий уровень
    void restart();

    // начать следующий уровень
    void startNextLevel();

    // уровень с номером level завершён
    void levelCompleted(int level);
}
