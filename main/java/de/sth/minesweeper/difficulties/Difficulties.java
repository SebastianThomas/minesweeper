package de.sth.minesweeper.difficulties;

public enum Difficulties {
    EASY,
    MEDIUM,
    ADVANCED,
    PROFESSIONAL;

    public static Difficulty getDifficulty(Difficulties difficulty) {
//        if (difficulty == EASY) return new Difficulty(9, 9, 35);
        if (difficulty == EASY) return new Difficulty(9, 9, 10);

//        if (difficulty == MEDIUM) return new Difficulty(16, 16, 99);
        if (difficulty == MEDIUM) return new Difficulty(16, 16, 40);
//        if (difficulty == ADVANCED) return new Difficulty(22, 22, 170);
        if (difficulty == ADVANCED) return new Difficulty(22, 22, 99);


//        if (difficulty == PROFESSIONAL) return new Difficulty(25, 25, 230);
        if (difficulty == PROFESSIONAL) return new Difficulty(25, 25, 170);
        return null;
    }
}