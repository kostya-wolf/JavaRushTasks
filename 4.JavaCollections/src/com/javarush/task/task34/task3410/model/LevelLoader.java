package com.javarush.task.task34.task3410.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        char[][] levelArr = {};
        try (RandomAccessFile raf = new RandomAccessFile(levels.toFile(), "r")) {
            String allFileString = getAllFileString(raf);
            int levelsCount = getLevelsCount(raf, allFileString);
            int realLevel = level <= levelsCount ? level : level % levelsCount == 0 ? levelsCount : level % levelsCount;
            levelArr = readLevel(raf, allFileString, realLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        for (int y = 1; y <= levelArr[0].length; y++) {
            for (int x = 1; x <= levelArr.length; x++) {
                switch (levelArr[x-1][y-1]) {
                    case 'X': {
                        walls.add(new Wall(getCoord(x), getCoord(y)));
                        break;
                    }
                    case '*': {
                        boxes.add(new Box(getCoord(x), getCoord(y)));
                        break;
                    }
                    case '.': {
                        homes.add(new Home(getCoord(x), getCoord(y)));
                        break;
                    }
                    case '&': {
                        homes.add(new Home(getCoord(x), getCoord(y)));
                        boxes.add(new Box(getCoord(x), getCoord(y)));
                        break;
                    }
                    case '@': {
                        player = new Player(getCoord(x), getCoord(y));
                        break;
                    }
                }
            }
        }

        return new GameObjects(walls, boxes, homes, player);
    }

    // Читаем весь файл в строку
    private String getAllFileString(RandomAccessFile raf) throws IOException {
        byte[] b = new byte[(int) raf.length()];
        raf.readFully(b);
        return new String(b);
    }

    // Узнаём число уровней
    private int getLevelsCount(RandomAccessFile raf, String allFileString) throws IOException {
        int lastIndexOfMaze = allFileString.lastIndexOf("Maze:");
        raf.seek(lastIndexOfMaze);
        return Integer.parseInt(raf.readLine().split(": ")[1]);
    }

    // Заносим уровень в двойной масссив
    private char[][] readLevel(RandomAccessFile raf, String allFileString, int realLevel) throws IOException {
        int levelIndex = allFileString.indexOf("Maze: " + realLevel);
        raf.seek(levelIndex);
        String maze = raf.readLine();
        String fileOffset = raf.readLine();

        int sizeX = Integer.parseInt(raf.readLine().split(": ")[1]);
        int sizeY = Integer.parseInt(raf.readLine().split(": ")[1]);
        String end = raf.readLine();
        int length = Integer.parseInt(raf.readLine().split(": ")[1]);
        raf.readLine();

        char[][] result = new char[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                result[x][y] = (char) raf.read();
            }
            System.out.println(raf.readLine());
        }
        return result;
    }

    // рассчитываем координаты
    private int getCoord(int coord) {
        return coord * FIELD_CELL_SIZE - FIELD_CELL_SIZE / 2;
    }
}
