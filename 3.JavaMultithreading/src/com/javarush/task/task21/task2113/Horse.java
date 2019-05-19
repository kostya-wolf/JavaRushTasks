package com.javarush.task.task21.task2113;

/**
 * Created by Волковы on 02.07.2017.
 */
public class Horse {
    private String name;
    private double speed;
    private double distance;

    public Horse(String name, double speed, double distance) {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }

    public void move(){
        distance += speed*Math.random();
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < Math.floor(distance); j++) {
            sb.append('.');
        }
        sb.append(name);
        System.out.println(sb.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
