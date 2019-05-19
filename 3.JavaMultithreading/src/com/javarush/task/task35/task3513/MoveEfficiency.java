package com.javarush.task.task35.task3513;

/**
 * Created by Волковы on 29.09.2018.
 */
public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        int diff = Integer.compare(this.numberOfEmptyTiles, o.numberOfEmptyTiles);
        if (diff == 0) {
            diff = Integer.compare(this.score, o.score);
        }
        return diff;
    }
}
