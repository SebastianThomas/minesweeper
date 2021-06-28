package de.sth.minesweeper;

import de.sth.minesweeper.difficulties.Difficulties;

public class Start {
    public static void main(String... args) {
        new MainMenu();
    }

    public static void mainGame() {
        MineSweeper.start(true, Difficulties.getDifficulty(Difficulties.ADVANCED));
    }
}
