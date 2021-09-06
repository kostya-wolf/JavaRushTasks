package com.javarush.task.task34.task3410.controller;

import com.javarush.task.task34.task3410.model.Direction;
import com.javarush.task.task34.task3410.model.GameObjects;
import com.javarush.task.task34.task3410.model.Model;
import com.javarush.task.task34.task3410.view.View;

public class Controller implements EventListener {

    private Model model;
    private View view;

    public Controller() {
        this.model = new Model();
        this.view = new View(this);
        this.view.init();
        this.model.restart();
        this.model.setEventListener(this);
        this.view.setEventListener(this);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    @Override
    public void move(Direction direction) {
        this.model.move(direction);
        this.view.update();
    }

    @Override
    public void restart() {
        this.model.restart();
        this.view.update();
    }

    @Override
    public void startNextLevel() {
        this.model.startNextLevel();
        this.view.update();
    }

    @Override
    public void levelCompleted(int level) {
        this.view.completed(level);
    }

    public GameObjects getGameObjects() {
        return this.model.getGameObjects();
    }
}
