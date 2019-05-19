package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

/**
 * Интерфейс-маркер, по нему мы определяем, является ли переданный объект событием
 */
public interface EventDataRow {
    EventType getType();

    Date getDate();

    int getTime();
}
