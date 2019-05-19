package com.javarush.task.task29.task2909.human;

/**
 * Created by Волковы on 17.10.2017.
 */
public class Soldier extends Human {

    public Soldier(String name, int age) {
        super(name, age);
    }

    public void fight() {
    }

    public void live() {
        fight();
    }
}
