package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Волковы on 07.09.2018.
 */
public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        List<Dish> randomDishes = new ArrayList<>();
        int cycle = ((int) (Math.random() * 5)) + 1;
        for (int i = 0; i < cycle; i++) {
            int ordinal = (int) (Math.random() * Dish.values().length);
            randomDishes.add(Dish.values()[ordinal]);
        }
        this.dishes = randomDishes;
    }
}
