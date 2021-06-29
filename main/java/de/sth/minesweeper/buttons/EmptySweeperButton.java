package de.sth.minesweeper.buttons;

import de.sth.minesweeper.MineSweeper;
import de.sth.minesweeper.constants.ColorConstant;

import java.awt.*;

public class EmptySweeperButton extends SweeperButton {
    private int bombsAround;

    public EmptySweeperButton(MineSweeper sweeper, int x, int y, int width, int height, int bombsAround) {
        super(sweeper, x, y, width, height);
        this.bombsAround = bombsAround;
    }

    @Override
    public void reveal() {
        this.setIcon(null);
        this.setForeground(ColorConstant.FG_Color);
        this.setText(this.bombsAround != 0 ? String.valueOf(this.bombsAround) : "");
    }

    @Override
    public void reveal(boolean gameOver) {
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