package de.sth.minesweeper.difficulties;

public enum Difficulties {
    EASY,
    MEDIUM,
    ADVANCED,
    PROFESSIONAL;

    public static Difficulty getDifficulty(Difficulties difficulty) {
        if (difficulty == EASY) return new Difficulty(16, 16, 50);
//            if (difficulty == MEDIUM) return new Difficulty(16, 16, 50);
        if (difficulty == ADVANCED) return new Difficulty(24, 24, 99);
//            if (difficulty == PROFESSIONAL) return new Difficulty(16, 16, 50);
        return null;
    }
}