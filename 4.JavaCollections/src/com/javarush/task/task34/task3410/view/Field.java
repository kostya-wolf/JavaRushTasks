package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;

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
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }
}
