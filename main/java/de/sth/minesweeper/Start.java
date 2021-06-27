package de.sth.minesweeper;

public class Start {
    public static void main(String... args) {
        new MainMenu();
    }

    public static void mainGame() {
        MineSweeper.start(true);
    }
}
