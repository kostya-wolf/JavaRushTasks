package com.javarush.task.task29.task2909.car;

/**
 * Created by Волковы on 25.10.2017.
 */
public class Cabriolet extends Car {
    public Cabriolet(int numberOfPassengers) {
        super(CABRIOLET, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return Car.MAX_CABRIOLET_SPEED;
    }
}
