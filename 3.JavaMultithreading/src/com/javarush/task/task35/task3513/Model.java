package com.javarush.task.task35.task3513;

import java.util.*;

/**
 * Created by Волковы on 28.09.2018.
 */
public class Model {
    private static final int FIELD_WIDTH = 4;

    private Tile[][] gameTiles;

    int score;
    int maxTile;

    private Stack<Tile[][]> previousStates = new Stack();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
            resetGameTiles();
    }

    private void saveState(Tile[][] tilesState) {
        Tile[][] newTiles = new Tile[tilesState.length][tilesState.length];
        for (int i = 0; i < tilesState.length; i++) {
            for (int j = 0; j < tilesState.length; j++) {
                newTiles[i][j] = new Tile(tilesState[i][j].value);
            }
        }
        previousScores.push(score);
        previousStates.push(newTiles);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()){
            gameTiles = previousStates.pop();
            score = previousScores.pop();
            isSaveNeeded = true;
        }
    }

    public void resetGameTiles() {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        score = 0;
        maxTile = 0;
        addTile();
        addTile();
    }

    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    public void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        boolean changed = false;
        for (Tile[] tiles : gameTiles) {
            if (compressTiles(tiles) | mergeTiles(tiles)) {
                changed = true;
            };
        }
        if (changed) {
            addTile();
        }
        isSaveNeeded = true;
    }

    public void up() {
        saveState(gameTiles);
        rotate90Left();
        left();
        rotate90Left();
        rotate90Left();
        rotate90Left();
    }

    public void right() {
        saveState(gameTiles);
        rotate90Left();
        rotate90Left();
        left();
        rotate90Left();
        rotate90Left();
    }

    public void down() {
        saveState(gameTiles);
        rotate90Left();
        rotate90Left();
        rotate90Left();
        left();
        rotate90Left();
    }

    private void rotate90Left() {
        Tile[][] result = new Tile[gameTiles.length][gameTiles.length];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                result[gameTiles.length - 1 - j][i] = gameTiles[i][j];
            }
        }
        gameTiles = result;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean changed = false;
        for (int i = 0; i < tiles.length - 1; i++)
            for (int j = 0; j < tiles.length - 1 - i; j++) {
                if (tiles[j].isEmpty()) {
                    Tile tempTile = tiles[j];
                    tiles[j] = tiles[j + 1];
                    tiles[j + 1] = tempTile;
                    if (!tiles[j].isEmpty()) {
                        changed = true;
                    }
                }
            }
        return changed;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean changed = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if ((tiles[i].value != 0) && (tiles[i].value == tiles[i + 1].value)) {
                tiles[i].value += tiles[i + 1].value;
                tiles[i + 1].value = 0;
                changed = true;
                score += tiles[i].value;
                if (maxTile < tiles[i].value) {
                    maxTile = tiles[i].value;
                }
            }
        }
        compressTiles(tiles);
        return changed;
    }

    private void addTile() {
        List<Tile> emptyTilesList = getEmptyTiles();
        if (!emptyTilesList.isEmpty()) {
            int randomTileIndex = (int) (Math.random() * emptyTilesList.size());
            emptyTilesList.get(randomTileIndex).value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    result.add(gameTiles[i][j]);
                }
            }
        }
        return result;
    }

    public boolean canMove() {
        for (int i = 0; i < gameTiles.length - 1; i++) {
            for (int j = 0; j < gameTiles.length - 1; j++) {
                if (gameTiles[i][j].isEmpty()
                        || gameTiles[i][j].value == gameTiles[i + 1][j].value
                        || gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }

        int max = gameTiles.length - 1;
        for (int j = 0; j < gameTiles.length; j++) {
            if (gameTiles[max][j].isEmpty()
                    || ((j != max) && (gameTiles[max][j].value == gameTiles[max][j + 1].value))) {
                return true;
            }
        }
        for (int i = 0; i < gameTiles.length; i++) {
            if (gameTiles[i][max].isEmpty()
                    || ((i != max) && (gameTiles[i][max].value == gameTiles[i + 1][max].value))) {
                return true;
            }
        }

        if (gameTiles[max][max].isEmpty()
                || gameTiles[max][max].value == gameTiles[max - 1][max].value
                || gameTiles[max][max].value == gameTiles[max][max - 1].value) {
            return true;
        }
        return false;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean hasBoardChanged() {
        return getWeight(gameTiles) != getWeight(previousStates.peek());
    }

    private int getWeight(Tile[][] tiles) {
        int result = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                result += tiles[i][j].value;
            }
        }
        return result;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();

        int numberOfEmptyTiles = 0;
        int score = this.score;
        if (hasBoardChanged()) {
            for (int i = 0; i < gameTiles.length; i++) {
                for (int j = 0; j < gameTiles.length; j++) {
                    if (gameTiles[i][j].value == 0) {
                        numberOfEmptyTiles++;
                    }
                    ;
                }
            }
        } else {
            numberOfEmptyTiles = -1;
            score = 0;
        }
        rollback();
        return new MoveEfficiency(numberOfEmptyTiles, score, move);
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> queue = new PriorityQueue(4, Collections.reverseOrder());
        queue.add(getMoveEfficiency(this::left));
        queue.add(getMoveEfficiency(this::right));
        queue.add(getMoveEfficiency(this::up));
        queue.add(getMoveEfficiency(this::down));
        queue.poll().getMove().move();
    }
}
