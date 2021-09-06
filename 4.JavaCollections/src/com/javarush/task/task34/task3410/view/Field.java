package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.GameObjects;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private View view;
    private EventListener eventListener;

    public Field(View view) {
        this.view = view;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        GameObjects gameObjects = this.view.getGameObjects();
        gameObjects.getAll().forEach(o -> o.draw(g));
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
