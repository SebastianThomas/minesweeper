package de.sth.minesweeper.buttons;

import de.sth.minesweeper.MineSweeper;
import de.sth.minesweeper.constants.ColorConstant;
import de.sth.minesweeper.logging.Logger;
import de.sth.minesweeper.stats.GameStatistic;

import java.awt.*;

public class EmptySweeperButton extends SweeperButton {
    private int bombsAround;

    public EmptySweeperButton(MineSweeper sweeper, GameStatistic stats, int x, int y, int width, int height, int bombsAround) {
        super(sweeper, stats, x, y, width, height);
        this.bombsAround = bombsAround;
    }

    @Override
    public void reveal() {
        Logger.getInstance().log("Reveal: E(" + this.x + "|" + this.y + ") --> Empty");
        this.setIcon(null);
        this.setForeground(ColorConstant.FG_Color);
        this.setText(this.bombsAround != 0 ? String.valueOf(this.bombsAround) : "");
    }

    @Override
    public void reveal(boolean gameOver) {
        Logger.getInstance().log("Reveal: E(" + this.x + "|" + this.y + ") --> Empty");
        int rgb = this.bombsAround == 0 ? 180 : 255;
        if (this.revealed) rgb = 80;
        this.setForeground(new Color(rgb, rgb, rgb));

        this.setIcon(null);
        if (gameOver) this.setText(String.valueOf(this.bombsAround));
        else this.setText(this.bombsAround != 0 ? String.valueOf(this.bombsAround) : "");
    }

    @Override
    public int getBombsAround() {
        return this.bombsAround;
    }
}
