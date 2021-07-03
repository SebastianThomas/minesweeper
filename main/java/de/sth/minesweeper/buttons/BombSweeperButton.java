package de.sth.minesweeper.buttons;

import de.sth.minesweeper.BombException;
import de.sth.minesweeper.MineSweeper;
import de.sth.minesweeper.logging.Logger;
import de.sth.minesweeper.stats.GameStatistic;

public class BombSweeperButton extends SweeperButton {
    public BombSweeperButton(MineSweeper sweeper, GameStatistic stats, int x, int y, int width, int height) {
        super(sweeper, stats, x, y, width, height);
    }

    @Override
    public void reveal() throws BombException {
        Logger.getInstance().log("Reveal: B(" + this.x + "|" + this.y + ") --> BOMB");
        throw new BombException();
    }

    @Override
    public void reveal(boolean b) throws BombException {
        Logger.getInstance().log("Reveal: B(" + this.x + "|" + this.y + ") --> BOMB");
        throw new BombException();
    }

    @Override
    public int getBombsAround() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}