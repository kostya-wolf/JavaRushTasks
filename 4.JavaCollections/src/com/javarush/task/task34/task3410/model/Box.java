package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {
    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        int x1 = getX() - getWidth() / 2;
        int y1 = getY() - getHeight() / 2;
        graphics.drawRect(x1, y1, getWidth(), getHeight());
        graphics.drawLine(x1, y1, x1 + getWidth(), y1 + getHeight());
        graphics.drawLine(x1 + getWidth(), y1, x1, y1 + getHeight());
    }

    @Override
    public void move(int x, int y) {
        this.setX(getX() + x);
        this.setY(getY() + y);
    }
}
