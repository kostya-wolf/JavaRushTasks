package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Волковы on 25.08.2018.
 */
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();
        String s;
        while (true) {
            writeMessage("Выберите блюдо: " + Dish.allDishesToString());
            s = readString();
            if ("exit".equals(s)) {
                return dishes;
            }
            try {
                Dish dish = Dish.valueOf(s);
                dishes.add(dish);
            } catch (IllegalArgumentException e) {
                writeMessage("Такого блюда нет");
            }
        }
    }
}
