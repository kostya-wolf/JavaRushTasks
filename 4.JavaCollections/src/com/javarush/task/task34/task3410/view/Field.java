package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.Direction;
import com.javarush.task.task34.task3410.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel {
    private View view;
    private EventListener eventListener;

    public Field(View view) {
        this.view = view;
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
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

    public class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                eventListener.move(Direction.LEFT);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                eventListener.move(Direction.RIGHT);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                eventListener.move(Direction.UP);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                eventListener.move(Direction.DOWN);
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                eventListener.restart();
            }
        }
    }
}
