package com.javarush.task.task34.task3410.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        Set<Wall> walls = new HashSet<>();
        walls.add(new Wall(20, 20));
        walls.add(new Wall(500, 100));
        walls.add(new Wall(100, 500));
        walls.add(new Wall(420, 420));

        Set<Box> boxes = new HashSet<>();
        boxes.add(new Box(200, 200));
        boxes.add(new Box(220, 220));
        boxes.add(new Box(260, 200));
        Set<Home> homes = new HashSet<>();
        homes.add(new Home(100, 100));
        homes.add(new Home(120, 100));
        homes.add(new Home(100, 120));
        Player player = new Player(260, 260);
        GameObjects gameObjects = new GameObjects(walls, boxes, homes, player);
        return gameObjects;
    }
}
