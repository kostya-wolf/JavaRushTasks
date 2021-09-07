package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;

public class Model {
    public static final int FIELD_CELL_SIZE = 20;

    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("com/javarush/task/task34/task3410/res/levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return this.gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(this.currentLevel);
    }

    public void startNextLevel() {
        this.currentLevel++;
        restart();
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction)) {
            return;
        }
        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }
        moveObject(player, direction);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        return gameObjects.getWalls()
                .stream()
                .anyMatch(wall -> gameObject.isCollision(wall, direction));
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Player player = gameObjects.getPlayer();

        // ищем столкновение с ящиком
        Box boxWithColision = null;
        for (Box b : gameObjects.getBoxes()) {
            if (player.isCollision(b, direction)) {
                boxWithColision = b;
                break;
            }
        }
        if (boxWithColision != null) {
            Box box2Colision = null;
            for (Box b : gameObjects.getBoxes()) {
                if (boxWithColision.isCollision(b, direction)) {
                    box2Colision = b;
                    break;
                }
            }
            // ищем за ящиком ящик или стену
            if (box2Colision != null || checkWallCollision(boxWithColision, direction)) {
                return true;
            } else {
                moveObject(boxWithColision, direction);
                return false;
            }
        }
        return false;
    }

    public void moveObject(Movable movable, Direction direction) {
        CollisionObject object = (CollisionObject) movable;
        int newX = 0;
        int newY = 0;
        switch (direction) {
            case LEFT:
                newX -= FIELD_CELL_SIZE;
                break;
            case RIGHT:
                newX += FIELD_CELL_SIZE;
                break;
            case UP:
                newY -= FIELD_CELL_SIZE;
                break;
            case DOWN:
                newY += FIELD_CELL_SIZE;
                break;
        }
        movable.move(newX, newY);
    }

    // на всех ли домах стоят ящики, их координаты должны совпадать)
    public void checkCompletion() {
        boolean complete = getGameObjects().getHomes()
                .stream()
                .allMatch(home -> {
                    return getGameObjects().getBoxes()
                            .stream()
                            .anyMatch(box -> {
                                return box.getX() == home.getX()
                                        && box.getY() == home.getY();
                            });
                });
        if (complete) {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
