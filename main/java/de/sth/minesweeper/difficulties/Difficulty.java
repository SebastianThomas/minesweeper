package de.sth.minesweeper.difficulties;

public class Difficulty {
    public int ROWS;
    public int COLS;
    public int BOMBS;

    Difficulty(int rows, int cols, int bombs) {
        this.ROWS = rows;
        this.COLS = cols;
        this.BOMBS = bombs;
    }
}
