package com.javarush.task.task27.task2712.kitchen;

/**
 * Блюдо
 */
public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration; // время приготовления в минутах

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder sb = new StringBuilder();
        for (Dish dish : Dish.values()) {
            sb.append(dish + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
