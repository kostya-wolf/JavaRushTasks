package com.javarush.task.task29.task2909.car;

/**
 * Created by Волковы on 25.10.2017.
 */
public class Sedan extends Car {
    public Sedan(int numberOfPassengers) {
        super(SEDAN, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return Car.MAX_SEDAN_SPEED;
    }
}
