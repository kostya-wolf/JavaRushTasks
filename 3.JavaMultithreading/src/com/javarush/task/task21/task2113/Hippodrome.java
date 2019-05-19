package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Волковы on 02.07.2017.
 */
public class Hippodrome {

    static Hippodrome game;

    private List<Horse> horses;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move(){
        for (Horse h : horses) {
            h.move();
        }
    }

    public void print(){
        for (Horse h : horses) {
            h.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public Horse getWinner(){
        double maxDistance = 0;
        Horse winner = null;
        for (Horse h : horses) {
            if (h.getDistance() > maxDistance){
                maxDistance = h.getDistance();
                winner = h;
            }
        }
        return winner;
    }

    public void printWinner(){
        System.out.println("Winner is "+getWinner().getName()+"!");
    }
    
    public List<Horse> getHorses() {
        return horses;
    }

    public static void main(String[] args) {
        Horse gonka = new Horse("Gonka", 3d, 0d);
        Horse pilot = new Horse("Pilot", 3d, 0d);
        Horse tachka = new Horse("Tachka", 3d, 0d);
        game = new Hippodrome(Arrays.asList(gonka, pilot, tachka));
        game.getHorses().add(gonka);
        game.getHorses().add(pilot);
        game.getHorses().add(tachka);
        game.run();
        game.printWinner();
    }
}
