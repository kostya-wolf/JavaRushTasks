package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.util.Date;
import java.util.List;

/**
 * Событие - повар приготовил заказ
 */
public class CookedOrderEventDataRow implements EventDataRow {

    private String tabletName;          // имя планшета
    private String cookName;            // имя повара
    private int cookingTimeSeconds;     // время приготовления заказа в секундах
    private List<Dish> cookingDishs;    // список блюд для приготовления
    private Date currentDate;           // текущая дата

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}
