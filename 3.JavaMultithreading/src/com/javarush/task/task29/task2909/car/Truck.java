package com.javarush.task.task29.task2909.car;

/**
 * Created by Волковы on 25.10.2017.
 */
public class Truck extends Car {
    public Truck(int numberOfPassengers) {
        super(TRUCK, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return Car.MAX_TRUCK_SPEED;
    }
}
