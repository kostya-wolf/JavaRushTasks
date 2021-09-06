package com.javarush.task.task34.task3410.model;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        Set<Wall> walls = new HashSet<>();
        walls.add(new Wall(10, 10));
        walls.add(new Wall(500, 100));
        walls.add(new Wall(100, 500));
        walls.add(new Wall(390, 390));
        Box box = new Box(200, 200);
        Home home = new Home(100, 100);
        Player player = new Player(250, 250);
        return new GameObjects(walls, Collections.singleton(box), Collections.singleton(home), player);
    }
}
