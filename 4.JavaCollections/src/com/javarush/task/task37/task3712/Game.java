package com.javarush.task.task37.task3712;

abstract public class Game {
    public void run() {
        prepareForTheGame();
        playGame();
        congratulateWinner();
    }

    abstract public void prepareForTheGame();

    abstract public void playGame();

    abstract public void congratulateWinner();
}
